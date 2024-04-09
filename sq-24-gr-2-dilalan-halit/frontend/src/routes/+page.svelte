<script>
	import welcome from '$lib/images/svelte-welcome.webp';
	import welcome_fallback from '$lib/images/svelte-welcome.png';
	import { goto } from '$app/navigation';
	import { Cart } from '$lib/cart';
	import { BACKEND_URL } from '../config';

	export let data;
	let { products } = data;

	let isDialogOpen = false;

	let selectedCategory = 'TopProducts'; // default category

	// Function to load products based on selected category
	async function loadProducts() {
		let url = `${BACKEND_URL}/api/products`;
		if(selectedCategory !== 'TopProducts') {
			url = `${BACKEND_URL}/api/products?category=${selectedCategory}`;
		}

		const response = await fetch(url);
		const data = await response.json();
		products = data;
	}

	function sortPriceLowHigh() {
		products = [...products].sort((a, b) => a.price - b.price);
	}

	function sortPriceHighLow() {
		products = [...products].sort((a, b) => b.price - a.price);
	}

	function sortMostPopular() {
		products = [...products].sort((a, b) => a.id - b.id);
	}
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<div>
	<div>
		{#if isDialogOpen}
			<div class="relative z-40 lg:hidden" role="dialog" aria-modal="true">

				<div class="fixed inset-0 bg-black bg-opacity-25"></div>

				<div class="fixed inset-0 z-40 flex">

					<div class="relative ml-auto flex h-full w-full max-w-xs flex-col overflow-y-auto bg-white py-4 pb-12 shadow-xl">
						<div class="flex items-center justify-between px-4">
							<h2 class="text-lg font-medium text-gray-900">Filters</h2>
							<button type="button" class="-mr-2 flex h-10 w-10 items-center justify-center rounded-md bg-white p-2 text-gray-400" on:click={() => isDialogOpen = false}>
								<span class="sr-only">Close menu</span>
								<svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" aria-hidden="true">
									<path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
								</svg>
							</button>
						</div>

						<!-- Filters -->
						<form class="mt-4 border-t border-gray-200">

							<div class="border-t border-gray-200 px-4 py-6">
								<h3 class="-mx-2 -my-3 flow-root">
									<!-- Expand/collapse section button -->
									<button type="button" class="flex w-full items-center justify-between bg-white px-2 py-3 text-gray-400 hover:text-gray-500" aria-controls="filter-section-mobile-0" aria-expanded="false">
										<span class="font-medium text-gray-900">Size</span>
										<span class="ml-6 flex items-center">
											<!-- Expand icon, show/hide based on section open state. -->
											<svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
												<path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z" />
											</svg>
											<!-- Collapse icon, show/hide based on section open state. -->
											<svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
												<path fill-rule="evenodd" d="M4 10a.75.75 0 01.75-.75h10.5a.75.75 0 010 1.5H4.75A.75.75 0 014 10z" clip-rule="evenodd" />
											</svg>
										</span>
									</button>
								</h3>
								<!-- Filter section, show/hide based on section state. -->
								<div class="pt-6" id="filter-section-mobile-0">
									<div class="space-y-6">
										<div class="flex items-center">
											<input id="filter-mobile-color-0" name="color" value="most-popular" type="radio" on:change={sortMostPopular} checked class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-color-0" class="ml-3 min-w-0 flex-1 text-gray-500">Most Popular</label>
										</div>
										<div class="flex items-center">
											<input id="filter-mobile-color-1" name="color" value="low-to-high" type="radio" on:change={sortPriceLowHigh} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-color-1" class="ml-3 min-w-0 flex-1 text-gray-500">Price: Low to High</label>
										</div>
										<div class="flex items-center">
											<input id="filter-mobile-color-2" name="color" value="high-to-low" type="radio" on:change={sortPriceHighLow} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-color-2" class="ml-3 min-w-0 flex-1 text-gray-500">Price: High to Low</label>
										</div>
									</div>
								</div>
							</div>
							<div class="border-t border-gray-200 px-4 py-6">
								<h3 class="-mx-2 -my-3 flow-root">
									<!-- Expand/collapse section button -->
									<button type="button" class="flex w-full items-center justify-between bg-white px-2 py-3 text-gray-400 hover:text-gray-500" aria-controls="filter-section-mobile-1" aria-expanded="false">
										<span class="font-medium text-gray-900">Category</span>
										<span class="ml-6 flex items-center">
											<!-- Expand icon, show/hide based on section open state. -->
											<svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
												<path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z" />
											</svg>
											<!-- Collapse icon, show/hide based on section open state. -->
											<svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
												<path fill-rule="evenodd" d="M4 10a.75.75 0 01.75-.75h10.5a.75.75 0 010 1.5H4.75A.75.75 0 014 10z" clip-rule="evenodd" />
											</svg>
										</span>
									</button>
								</h3>
								<!-- Filter section, show/hide based on section state. -->
								<div class="pt-6" id="filter-section-mobile-1">
									<div class="space-y-6">
										<div class="flex items-center">
											<input id="filter-mobile-category-0" name="category" value="TopProducts" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-category-0" class="ml-3 min-w-0 flex-1 text-gray-500">Top Products</label>
										</div>
										<div class="flex items-center">
											<input id="filter-mobile-category-1" name="category" value="Furniture" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-category-1" class="ml-3 min-w-0 flex-1 text-gray-500">Furniture</label>
										</div>
										<div class="flex items-center">
											<input id="filter-mobile-category-2" name="category" value="Electronics" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-category-2" class="ml-3 min-w-0 flex-1 text-gray-500">Electronics</label>
										</div>
										<div class="flex items-center">
											<input id="filter-mobile-category-3" name="category" value="Leisure Goods" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
											<label for="filter-mobile-category-3" class="ml-3 min-w-0 flex-1 text-gray-500">Leisure Goods</label>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		{/if}

		<main class="mx-auto max-w-7xl px-4 sm:px-6 lg:px-8">
			<div class="flex items-baseline justify-between border-b border-gray-200 pb-6 pt-24">
				<h1 class="w-full">
        <span class="welcome block relative w-full h-0 pb-[24.22%]">
            <picture>
                <source srcset={welcome} type="image/webp" />
                <img src={welcome_fallback} alt="Welcome" class="absolute w-full h-full top-0 block object-cover" />
            </picture>
        </span>
				</h1>
				<div class="flex items-center">
					<button type="button" class="-m-2 ml-5 p-2 text-gray-400 hover:text-gray-500 sm:ml-7">
						<span class="sr-only">View grid</span>
						<svg class="h-5 w-5" aria-hidden="true" viewBox="0 0 20 20" fill="currentColor">
							<path fill-rule="evenodd" d="M4.25 2A2.25 2.25 0 002 4.25v2.5A2.25 2.25 0 004.25 9h2.5A2.25 2.25 0 009 6.75v-2.5A2.25 2.25 0 006.75 2h-2.5zm0 9A2.25 2.25 0 002 13.25v2.5A2.25 2.25 0 004.25 18h2.5A2.25 2.25 0 009 15.75v-2.5A2.25 2.25 0 006.75 11h-2.5zm9-9A2.25 2.25 0 0011 4.25v2.5A2.25 2.25 0 0013.25 9h2.5A2.25 2.25 0 0018 6.75v-2.5A2.25 2.25 0 0015.75 2h-2.5zm0 9A2.25 2.25 0 0011 13.25v2.5A2.25 2.25 0 0013.25 18h2.5A2.25 2.25 0 0018 15.75v-2.5A2.25 2.25 0 0015.75 11h-2.5z" clip-rule="evenodd" />
						</svg>
					</button>
					<button type="button" class="-m-2 ml-4 p-2 text-gray-400 hover:text-gray-500 sm:ml-6 lg:hidden" on:click={() => isDialogOpen = true}>
						<span class="sr-only">Filters</span>
						<svg class="h-5 w-5" aria-hidden="true" viewBox="0 0 20 20" fill="currentColor">
							<path fill-rule="evenodd" d="M2.628 1.601C5.028 1.206 7.49 1 10 1s4.973.206 7.372.601a.75.75 0 01.628.74v2.288a2.25 2.25 0 01-.659 1.59l-4.682 4.683a2.25 2.25 0 00-.659 1.59v3.037c0 .684-.31 1.33-.844 1.757l-1.937 1.55A.75.75 0 018 18.25v-5.757a2.25 2.25 0 00-.659-1.591L2.659 6.22A2.25 2.25 0 012 4.629V2.34a.75.75 0 01.628-.74z" clip-rule="evenodd" />
						</svg>
					</button>
				</div>
			</div>

			<section aria-labelledby="products-heading" class="pb-24 pt-6">
				<h2 id="products-heading" class="sr-only">Products</h2>

				<div class="grid grid-cols-1 gap-x-8 gap-y-10 lg:grid-cols-4">
					<!-- Filters -->
					<form class="hidden lg:block">

						<div class="border-b border-gray-200 py-6">
							<h3 class="-my-3 flow-root">
								<!-- Expand/collapse section button -->
								<button type="button" class="flex w-full items-center justify-between py-3 text-sm text-gray-400 hover:text-gray-500" aria-controls="filter-section-0" aria-expanded="false">
									<span class="text-gray-900 text-lg font-semibold mt-2">Sort</span>
									<span class="ml-6 flex items-center">
                    <!-- Expand icon, show/hide based on section open state. -->
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z" />
                    </svg>
										<!-- Collapse icon, show/hide based on section open state. -->
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path fill-rule="evenodd" d="M4 10a.75.75 0 01.75-.75h10.5a.75.75 0 010 1.5H4.75A.75.75 0 014 10z" clip-rule="evenodd" />
                    </svg>
                  </span>
								</button>
							</h3>
							<!-- Filter section, show/hide based on section state. -->
							<div class="pt-6" id="filter-section-0">
								<div class="space-y-4">
									<div class="flex items-center">
										<input id="filter-color-0" name="color" value="most-popular" type="radio" on:change={sortMostPopular} checked class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-color-0" class="ml-3 text-sm text-gray-600">Most Popular</label>
									</div>
									<div class="flex items-center">
										<input id="filter-color-1" name="color" value="low-to-high" type="radio" on:change={sortPriceLowHigh} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-color-1" class="ml-3 text-sm text-gray-600">Price: Low to High</label>
									</div>
									<div class="flex items-center">
										<input id="filter-color-2" name="color" value="high-to-low" type="radio" on:change={sortPriceHighLow} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-color-2" class="ml-3 text-sm text-gray-600">Price: High to Low</label>
									</div>
								</div>
							</div>
						</div>
						<div class="border-b border-gray-200 py-6">
							<h3 class="-my-3 flow-root">
								<!-- Expand/collapse section button -->
								<button type="button" class="flex w-full items-center justify-between py-3 text-sm text-gray-400 hover:text-gray-500" aria-controls="filter-section-1" aria-expanded="false">
									<span class="font-semibold text-gray-900 text-lg">Category</span>
									<span class="ml-6 flex items-center">
                    <!-- Expand icon, show/hide based on section open state. -->
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path d="M10.75 4.75a.75.75 0 00-1.5 0v4.5h-4.5a.75.75 0 000 1.5h4.5v4.5a.75.75 0 001.5 0v-4.5h4.5a.75.75 0 000-1.5h-4.5v-4.5z" />
                    </svg>
										<!-- Collapse icon, show/hide based on section open state. -->
                    <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                      <path fill-rule="evenodd" d="M4 10a.75.75 0 01.75-.75h10.5a.75.75 0 010 1.5H4.75A.75.75 0 014 10z" clip-rule="evenodd" />
                    </svg>
                  </span>
								</button>
							</h3>
							<!-- Filter section, show/hide based on section state. -->
							<div class="pt-6" id="filter-section-1">
								<div class="space-y-4">
									<div class="flex items-center">
										<input id="filter-category-0" name="category" value="TopProducts" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-category-0" class="ml-3 text-sm text-gray-600">Top Products</label>
									</div>
									<div class="flex items-center">
										<input id="filter-category-1" name="category" value="Furniture" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-category-1" class="ml-3 text-sm text-gray-600">Furniture</label>
									</div>
									<div class="flex items-center">
										<input id="filter-category-2" name="category" value="Electronics" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-category-2" class="ml-3 text-sm text-gray-600">Electronics</label>
									</div>
									<div class="flex items-center">
										<input id="filter-category-3" name="category" value="Leisure Goods" type="radio" bind:group={selectedCategory} on:change={loadProducts} class="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500">
										<label for="filter-category-3" class="ml-3 text-sm text-gray-600">Leisure Goods</label>
									</div>
								</div>
							</div>
						</div>
					</form>

					<!-- Product grid -->
					<div class="lg:col-span-3">
						<div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-3 gap-4">
							<!-- Iterate over products -->
							{#each products as product}
								<div class="product transition-transform transition-shadow border border-gray-300 rounded-md overflow-hidden hover:scale-105 hover:shadow-md p-2 m-2">
									<div class="cursor-pointer" on:click={() => goto(`/product_details/${product.id}`)}>
										<img class="h-48 w-full object-cover" src={`${BACKEND_URL}/static/products/${product.image}`} alt="{product.name}" />
										<h4 class="text-lg font-semibold mt-2">{product.name}</h4>
									</div>
									<div class="flex justify-between items-center mt-2">
										<p class="text-gray-700">€{product.price}</p>
										<button class="bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:bg-blue-600" on:click={() => Cart.add({...product, quantity: 1})}>Add to cart</button>
									</div>
								</div>
							{/each}
						</div>
					</div>
				</div>
			</section>
		</main>
	</div>
</div>



