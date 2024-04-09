import { z } from 'zod';
import { superValidate } from 'sveltekit-superforms/server';
import {type Action, fail, redirect} from '@sveltejs/kit';
import { BACKEND_URL } from '../../config';
import { countries } from 'countries-list';
import type {PageServerLoad} from './$types';

const initForm = z.object({
	username: z.string().default('JohnWick'),
	firstname: z.string().default('John'),
	lastname: z.string().default('Wick'),
	email: z.string().default('john.wick@apex.com'),
	password: z.string().default('password'),
	country: z.string().default('United States'),
	street: z.string().default('145 Hudson Street'),
	city: z.string().default('New York'),
	zip: z.string().default('10013')
});


const schema = z.object({
	username: z.string().min(5).max(20),
	firstname: z.string().min(2).max(20),
	lastname: z.string().min(2).max(20),
	email: z.string().email().max(50),
	password: z.string().min(8).max(128),
	country: z.string().min(4).max(44),
	street: z.string().min(1).max(182),
	city: z.string().min(1).max(190),
	zip: z.string().min(4).max(12)
});

export const load = (async ( ) => {
	// Server API:
	const form = await superValidate(initForm);

	const countryNames = Object.entries(countries).map(([, country]) => (
		country.name
	));

	return { form, countryNames }
}) satisfies PageServerLoad;

export const actions = {
	default: (async ({ request, params, url }) => {
		const form = await superValidate(request, schema);

		if (!form.valid) {
			return fail(400, { form });
		}

		const response = await fetch(`${BACKEND_URL}/api/user/register`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json' },
			body: JSON.stringify(form.data)
		});

		if (!response.ok) {
			form.valid = false;
			if (response.status === 409) {
				form.errors = { username: ['Username already taken'] };
			}

			return fail(400, { form });
		}

		return { form };
	}) satisfies Action
};