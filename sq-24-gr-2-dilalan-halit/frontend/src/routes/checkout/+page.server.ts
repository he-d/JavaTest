import {z} from 'zod';
import {superValidate} from 'sveltekit-superforms/server';
import {fail, redirect} from '@sveltejs/kit';
import {BACKEND_URL} from '../../config';
import {countries} from 'countries-list';
import type {Action, PageServerLoad} from './$types';

// Name has a default value just to display something in the form.
const schema = z.object({
	products: z.object({
		id: z.number().int().min(0),
		quantity: z.number().int().min(1),
	}).array(),
	shippingCountry: z.string().min(4).max(44),
	shippingStreet: z.string().min(1).max(182),
	shippingCity: z.string().min(1).max(190),
	shippingZip: z.string().min(4).max(12),
	checkbox: z.boolean()
});

export const load = (async ({ fetch }) => {
	// Server API:
	const form = await superValidate(schema);

	const countryNames = Object.entries(countries).map(([, country]) => (
		country.name
	));

	const fetchUser = async () => {
		const response = await fetch(`${BACKEND_URL}/api/user/current`);
		return await response.json();
	};

	return { form, countryNames, user: await fetchUser()}
}) satisfies PageServerLoad;


const submit = ( async ({ data, fetch }) => {
	let payload;

	const products = data.products.reduce((acc, item) => {
		acc[item.id] = item.quantity;
		return acc;
	}, {});

	if(data.checkbox) {
		const shippingAddress = {
			country: data.shippingCountry,
			city: data.shippingCity,
			street: data.shippingStreet,
			zip: data.shippingZip,
		};
		payload = { address: shippingAddress, products };
	} else {
		payload = { products };
	}

	return await fetch(`${BACKEND_URL}/api/submit_order`, {
		method: 'POST',
		headers: {'Content-Type': 'application/json'},
		body: JSON.stringify(payload),
		credentials: "include"
	})
});

export const actions = {
	default: (async ({ request, fetch }) => {
		const form = await superValidate(request, schema);

		// Convenient validation check:
		if (!form.valid && form.data.checkbox) {
			// Again, return { form } and things will just work.
			return fail(400, { form });
		}

		const response = await submit({ data: form.data, fetch })

		if(response.ok) {
			return redirect(303, '/confirmation');
		} else {
			return fail(500, { message: 'Something went wrong' });
		}
	}) satisfies Action
};