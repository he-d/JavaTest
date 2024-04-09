import { type HandleFetch, redirect } from '@sveltejs/kit';

export const handleFetch = (async ( { request, fetch } ) => {
    console.log(request.url);

    const response = await fetch(request);

    if (response.status === 401) {
        redirect(302,'/login')
    }

    return response;
}) satisfies HandleFetch;
