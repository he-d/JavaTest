import type {PageServerLoad} from './$types';

export const load = (async ( { cookies } ) => {
	cookies.delete('token', { path: '/' });
}) satisfies PageServerLoad;