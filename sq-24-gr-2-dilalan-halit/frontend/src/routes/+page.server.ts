import { BACKEND_URL } from '../config';
import type {PageServerLoad} from './$types';
import {error} from "@sveltejs/kit";
import type {Product} from "$lib/Product";

export const load = (async ( { fetch } ) => {
	const fetchProducts = async () => {
		const res = await fetch(`${BACKEND_URL}/api/products`);

		if (!res.ok) {
			error(500, {
				message: 'Products not found'
			});
		}

		return await res.json() as Product[];
	};

	return {
		products: await fetchProducts()
	};
}) satisfies PageServerLoad;

