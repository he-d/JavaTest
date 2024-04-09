import type {ProductDetails} from "./ProductDetails";
import type {PageServerLoad} from '../../$types';
import { error } from '@sveltejs/kit';
import {BACKEND_URL} from "../../../config";

export const load = (async ({ fetch, params }) => {
    const fetchProductDetails = async (id: number) => {
        const res = await fetch(`${BACKEND_URL}/api/products/${id}`);

        if (!res.ok) {
            error(404, {
                message: 'Product not found'
            });
        }

        return await res.json() as ProductDetails;
    };

    const productId = Number(params.productId);
    const product = await fetchProductDetails(productId);

    return {
        product
    };
}) satisfies PageServerLoad;