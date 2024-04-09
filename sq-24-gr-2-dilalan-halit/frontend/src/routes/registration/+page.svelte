<script lang="ts">
	import type { PageData } from './$types';
	import { superForm } from 'sveltekit-superforms/client';
	import {page} from "$app/stores";
	import {goto} from "$app/navigation";
	import {redirect} from "@sveltejs/kit";

	export let data: PageData;

	const { form, enhance, errors} = superForm(data.form, {
		applyAction: false,
		onResult({ result }) {
			console.log('Form submitted with', result.status);
			if (result.status === 200) {
				console.log('Form submitted successfully');
				const redirectUrl = $page.url.searchParams.get('redirect') || '/';

				// Workaround for goto not working in onResult for SuperForms
				goto(redirectUrl);
				redirect(302, redirectUrl);
			}
		}
	});
</script>

<form method="POST" use:enhance>
	<div class="flex min-h-full flex-col justify-center px-6 py-12 lg:px-8">

		<div class="border-b border-gray-900/10 pb-6">
			<div class="sm:mx-auto sm:w-full sm:max-w-sm">
				<img class="mx-auto h-10 w-auto" src="https://tailwindui.com/img/logos/mark.svg?color=indigo&shade=600" alt="Your Company">
				<h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Registration</h2>
			</div>

			<div class="mt-8 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6 ">
				<div class="sm:col-span-3 border-b border-gray-900/10 pb-10">
					<div>
						<label for="username" class="block text-sm font-medium leading-6 text-gray-900">Username</label>
						<div class="mt-2">
							<input name="username"
										 type="text"
										 bind:value={$form.username}
										 aria-invalid={$errors.username ? 'true' : undefined}
										 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
							{#if $errors.username}<span class="text-red-700 relative">{$errors.username}</span>{/if}
						</div>
					</div>

					<div class="mt-8">
						<label for="password" class="block text-sm font-medium leading-6 text-gray-900">Password</label>
						<div class="mt-2">
							<input name="password"
										 type="password"
										 bind:value={$form.password}
										 aria-invalid={$errors.password ? 'true' : undefined}
										 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
							{#if $errors.password}<span class="text-red-700 relative">{$errors.password}</span>{/if}
						</div>
					</div>
				</div>
				<div class="sm:col-span-3 border-b border-gray-900/10 pb-6"/>


				<div class="sm:col-span-3">
					<label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">First name</label>
					<div class="mt-2">
						<input type="text"
									 name="firstname"
									 bind:value={$form.firstname}
									 aria-invalid={$errors.firstname ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
						{#if $errors.firstname}<span class="text-red-700 relative">{$errors.firstname}</span>{/if}
					</div>
				</div>

				<div class="sm:col-span-3">
					<label for="last-name" class="block text-sm font-medium leading-6 text-gray-900">Last name</label>
					<div class="mt-2">
						<input type="text"
									 name="lastname"
									 bind:value={$form.lastname}
									 aria-invalid={$errors.lastname ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
						{#if $errors.lastname}<span class="text-red-700 relative">{$errors.lastname}</span>{/if}
					</div>
				</div>

				<div class="sm:col-span-4">
					<label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
					<div class="mt-2">
						<input name="email"
									 type="email"
									 bind:value={$form.email}
									 aria-invalid={$errors.email ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6">
						{#if $errors.email}<span class="text-red-700 relative">{$errors.email}</span>{/if}
					</div>
				</div>

				<div class="sm:col-span-3">
					<label for="country" class="block text-sm font-medium leading-6 text-gray-900">Country</label>
					<div class="mt-2">
						<select name="country"
										bind:value={$form.country}
										aria-invalid={$errors.country ? 'true' : undefined}
										class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6" >
							{#each data.countryNames as country}
								<option>{country}</option>
							{/each}
						</select>
						{#if $errors.country}<span class="text-red-700 relative">{$errors.country}</span>{/if}
					</div>
				</div>

				<div class="col-span-full">
					<label for="street-address" class="block text-sm font-medium leading-6 text-gray-900">Street address</label>
					<div class="mt-2">
						<input type="text"
									 name="street"
									 bind:value={$form.street}
									 aria-invalid={$errors.street ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
						{#if $errors.street}<span class="text-red-700 relative">{$errors.street}</span>{/if}
					</div>
				</div>

				<div class="sm:col-span-1 sm:col-start-1">
					<label for="postal-code" class="block text-sm font-medium leading-6 text-gray-900">ZIP / Postal code</label>
					<div class="mt-2">
						<input type="text"
									 name="zip"
									 bind:value={$form.zip}
									 aria-invalid={$errors.zip ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
						{#if $errors.zip}<span class="text-red-700 relative">{$errors.zip}</span>{/if}
					</div>
				</div>

				<div class="sm:col-span-3">
					<label for="city" class="block text-sm font-medium leading-6 text-gray-900">City</label>
					<div class="mt-2">
						<input type="text"
									 name="city"
									 bind:value={$form.city}
									 aria-invalid={$errors.city ? 'true' : undefined}
									 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
						{#if $errors.city}<span class="text-red-700 relative">{$errors.city}</span>{/if}
					</div>
				</div>
			</div>
		</div>

		<div class="py-6">
			<h2 class="text-base font-semibold leading-7 text-gray-900 mb-3">Notifications</h2>
			<div class="relative flex gap-x-3">
				<div class="flex h-6 items-center">
					<input id="comments" name="comments" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600">
				</div>
				<div class="text-sm leading-6">
					<p class="text-gray-500">Subscribe to receive notifications about our newest products, exclusive offers, and updates.</p>
				</div>
			</div>
		</div>
	</div>

	<div class="flex items-center justify-end gap-x-6">
		<button type="button" class="text-sm font-semibold leading-6 text-gray-900">Cancel</button>
		<button type="submit" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Save</button>
	</div>
</form>