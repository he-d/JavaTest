<script lang="ts">
	import { fade } from 'svelte/transition';
	import { page } from '$app/stores';
	import { goto } from '$app/navigation';
	import type {PageData} from "../../../.svelte-kit/types/src/routes/registration/$types";
	import { superForm } from 'sveltekit-superforms/client';
	import { redirect } from '@sveltejs/kit';

	let message: string

	let username = 'harald';
	let password = 'password';

	export let data: PageData;

	$: if (message) {
		setTimeout(() => {
			message = '';
		}, 2500);
	}

	// Client API:
	const { errors, enhance } = superForm(data.form, {
		applyAction: false,
		onResult({ result }) {
			console.log("onResult", result);

			if(result.status === 401) {
				message = "Invalid username or password";
			}

			if (result.status === 200) {
				sessionStorage.setItem('name', result.data.name);
				const redirectUrl = $page.url.searchParams.get('redirect') || '/';

				// Workaround for goto not working in onResult for SuperForms
				goto(redirectUrl);
				redirect(302, redirectUrl);
			}

		}
	});

</script>

	<div class="flex min-h-full flex-col justify-center px-6 py-10 lg:px-8">
		<div class="sm:mx-auto sm:w-full sm:max-w-sm">
			<img class="mx-auto h-20 w-auto" src="shopping-icon.png" alt="E-Store">
			<h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Sign in to your account</h2>
		</div>

		<div class="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
			{#if message}
				<p transition:fade class="alert bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative text-center mb-4">{message}</p>
			{/if}
			<form method="POST" use:enhance>
				<div class="space-y-6">
					<div>
						<label for="username" class="block text-sm font-medium leading-6 text-gray-900">Username</label>
						<div class="mt-2">
							<input
								type="text"
								bind:value={username}
								name="username"
								required
								aria-invalid={$errors.username ? 'true' : undefined}
								class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
							{#if $errors.username}<span class="text-red-700 relative">{$errors.username}</span>{/if}
						</div>
					</div>

					<div>
						<div class="flex items-center justify-between">
							<label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
							<button type="button" class="font-semibold text-indigo-600 hover:text-indigo-500" on:click={() => message="Have you already tried \"password\" ?"}>
								Forgot password?
							</button>
						</div>
						<div class="mt-2">
							<input
								bind:value={password}
								name="password"
								type="password"
								aria-invalid={$errors.password ? 'true' : undefined}
								required
								class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
							{#if $errors.password}<span class="text-red-700 relative">{$errors.password}</span>{/if}
						</div>
					</div>

					<div>
						<button type="submit" class="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Sign in</button>
					</div>
				</div>
			</form>
			<p class="mt-10 text-center text-sm text-gray-500">
				Not a member?
				<a href="/registration?redirect={$page.url.searchParams.get('redirect') || '/'}" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">Register Here!</a>
			</p>
		</div>
	</div>
```
