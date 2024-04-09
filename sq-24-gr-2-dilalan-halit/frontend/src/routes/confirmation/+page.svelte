<script lang="ts">
	import { onMount } from 'svelte';
	import {Cart} from "$lib/cart";

	onMount(async () => {
		Cart.clear();
	});
</script>


<div class="flex flex-col min-h-screen py-12 px-4 sm:px-6 lg:px-8">
	<div class="sm:mx-auto sm:w-full sm:max-w-md">
		<div class="bg-white py-8 px-4 shadow sm:rounded-lg sm:px-10">
			<h1 class="mb-4 text-center text-2xl font-extrabold text-gray-900">Shipping confirmation</h1>
			<p class="mb-2 text-center text-sm text-gray-600">Thank you for your trust!</p>
			<p class="text-center text-sm text-gray-600">Your order has been shipped and will arrive soon.</p>
		</div>
	</div>

	<div class="mt-10 sm:mx-auto sm:w-full sm:max-w-md text-center"> <!-- Adjust max-w-md instead of max-w-sm -->
		<a href="/" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500 text-xl">Go back the Shopping</a>
	</div>

</div>