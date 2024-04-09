<script lang="ts" xmlns="http://www.w3.org/1999/html">
	import {Cart} from '$lib/cart';
	import type {PageData} from './$types';
	import {superForm} from 'sveltekit-superforms/client';
	import {onMount} from "svelte";

	export let data: PageData;
	const user = data.user;

	const { form, enhance, errors } = superForm(data.form, {
		dataType: 'json'
	});

	onMount(async () => {
		$form.products = $Cart.map(item => {
			return { id: item.id, quantity: item.quantity };
		});
	});
</script>

<div class="flex min-h-full flex-col justify-center px-6 py-10 lg:px-8">

	<div class="border-b border-gray-900/10 pb-6">
		<div class="sm:mx-auto sm:w-full sm:max-w-sm">
			<img class="mx-auto h-20 w-auto" src="shopping-icon.png" alt="E-Store">
			<h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">Checkout</h2>
		</div>

		<div class="mt-8 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6 ">
			<div class="sm:col-span-3">
				<label for="first-name" class="block text-sm font-medium leading-6 text-gray-900">First name</label>
				<div class="mt-2">
					<input type="text"
								 name="firstname"
								 bind:value={user.firstname}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-3">
				<label for="last-name" class="block text-sm font-medium leading-6 text-gray-900">Last name</label>
				<div class="mt-2">
					<input type="text"
								 name="lastname"
								 bind:value={user.lastname}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-4">
				<label for="email" class="block text-sm font-medium leading-6 text-gray-900">Email address</label>
				<div class="mt-2">
					<input name="email"
								 type="email"
								 bind:value={user.email}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-3">
				<label for="country" class="block text-sm font-medium leading-6 text-gray-900">Country</label>
				<div class="mt-2">
					<input type="text"
								 name="country"
								 bind:value={user.address.country}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-3">
				<label for="street-address" class="block text-sm font-medium leading-6 text-gray-900">Street address</label>
				<div class="mt-2">
					<input type="text"
								 name="street"
								 bind:value={user.address.street}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-1 sm:col-start-1">
				<label for="postal-code" class="block text-sm font-medium leading-6 text-gray-900">ZIP / Postal code</label>
				<div class="mt-2">
					<input type="text"
								 name="zip"
								 bind:value={user.address.zip}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>

			<div class="sm:col-span-3">
				<label for="city" class="block text-sm font-medium leading-6 text-gray-900">City</label>
				<div class="mt-2">
					<input type="text"
								 name="city"
								 bind:value={user.address.city}
								 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
								 readonly />
				</div>
			</div>
		</div>
	</div>

	<div class="py-6">
		<h2 class="text-base font-semibold leading-7 text-gray-900 mb-3">Other Shipping Address</h2>
		<div class="relative flex gap-x-3">
			<div class="flex h-6 items-center">
				<input name="checkbox"
							 type="checkbox"
							 bind:checked={$form.checkbox}
							 class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-600">
			</div>
			<div class="text-sm leading-6">
				<p class="text-gray-500">Use a different delivery address</p>
			</div>
		</div>
	</div>

	<form method="POST" use:enhance>
		{#if $form.checkbox}
			<div class="border-b border-gray-900/10 pb-6">

				<div class="mt-2 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6 ">

					<div class="sm:col-span-3">
						<label for="country" class="block text-sm font-medium leading-6 text-gray-900">Country</label>
						<div class="mt-2">
							<select name="shippingCountry"
											bind:value={$form.shippingCountry}
											aria-invalid={$errors.shippingCountry ? 'true' : undefined}
											class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6" >
								{#each data.countryNames as country}
									<option>{country}</option>
								{/each}
							</select>
							{#if $errors.shippingCountry}<span class="text-red-700 relative">{$errors.shippingCountry}</span>{/if}
						</div>
					</div>

					<div class="sm:col-span-3">
						<label for="street-address" class="block text-sm font-medium leading-6 text-gray-900">Street address</label>
						<div class="mt-2">
							<input type="text"
										 name="shippingStreet"
										 bind:value={$form.shippingStreet}
										 aria-invalid={$errors.shippingStreet ? 'true' : undefined}
										 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6" />
							{#if $errors.shippingStreet}<span class="text-red-700 relative">{$errors.shippingStreet}</span>{/if}
						</div>
					</div>

					<div class="sm:col-span-1 sm:col-start-1">
						<label for="postal-code" class="block text-sm font-medium leading-6 text-gray-900">ZIP / Postal code</label>
						<div class="mt-2">
							<input type="text"
										 name="shippingZip"
										 bind:value={$form.shippingZip}
										 aria-invalid={$errors.shippingZip ? 'true' : undefined}
										 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										  />
							{#if $errors.shippingZip}<span class="text-red-700 relative">{$errors.shippingZip}</span>{/if}
						</div>
					</div>

					<div class="sm:col-span-3">
						<label for="city" class="block text-sm font-medium leading-6 text-gray-900">City</label>
						<div class="mt-2">
							<input type="text"
										 name="shippingCity"
										 bind:value={$form.shippingCity}
										 aria-invalid={$errors.shippingCity ? 'true' : undefined}
										 class="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										  />
							{#if $errors.shippingCity}<span class="text-red-700 relative">{$errors.shippingCity}</span>{/if}
						</div>
					</div>
				</div>
			</div>
		{/if}

		{#each $form.products as product}
			<input type="hidden" bind:value={product.id} />
			<input type="hidden" bind:value={product.quantity} />
		{/each}

		<div class="flex items-center justify-end gap-x-6">
			<button type="button" class="text-sm font-semibold leading-6 text-gray-900">Cancel</button>
			<button type="submit" class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">Save</button>
		</div>
	</form>
</div>
