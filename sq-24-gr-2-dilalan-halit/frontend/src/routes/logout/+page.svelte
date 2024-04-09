<script lang="ts">

	import { onMount } from 'svelte';
		onMount(async () => {
			sessionStorage.removeItem('name');
		});
</script>

<div class="flex min-h-full flex-col justify-center px-6 py-10">
	<div class="sm:mx-auto sm:w-full sm:max-w-md"> <!-- Adjust max-w-md instead of max-w-sm -->
		<img class="mx-auto h-20 w-auto" src="shopping-icon.png" alt="E-Store">
		<h2 class="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">You have been successfully signed out!</h2>
	</div>

	<div class="mt-10 sm:mx-auto sm:w-full sm:max-w-md text-center"> <!-- Adjust max-w-md instead of max-w-sm -->
		<a href="/" class="font-semibold leading-6 text-indigo-600 hover:text-indigo-500 text-xl">Go back the Shopping</a>
	</div>
</div>

