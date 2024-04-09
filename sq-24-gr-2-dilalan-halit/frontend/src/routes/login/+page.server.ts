import { z } from 'zod';
import { superValidate } from 'sveltekit-superforms/server';
import {type Action, fail} from '@sveltejs/kit';
import { BACKEND_URL } from '../../config';
import type {PageServerLoad} from './$types';

const schema = z.object({
    username: z.string().min(5).max(20).default('harald'),
    password: z.string().min(8).max(128).default('password'),
});

export const load = (async ( ) => {
    const form = await superValidate(schema);

    return { form }
}) satisfies PageServerLoad;

export const actions = {
    default: (async ( {request, fetch, cookies } ) => {
        const form = await superValidate(request, schema);


        if (!form.valid) {
            return fail(400, { form });
        }

        const response = await fetch(`${BACKEND_URL}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(form.data),
            credentials: 'include'
        });


        if (!response.ok) {
            form.valid = false;
            console.log(response);
            return fail(401, { form });
        }


        const { name } = await response.json();

        const cookie = response.headers.get("set-cookie");
        if (!cookie) {
            return fail(500, { form });
        }

        const token = cookie.split(";")[0].split("=")[1];

        cookies.set("token", token, {
            secure: false,
            path: "/",
            sameSite: 'strict',
        });

        return {form, name};
    }) satisfies Action
};