<script lang="ts">
	import { Cart } from '$lib/cart';
	import { goto } from '$app/navigation';
	import {BACKEND_URL} from "../../config";

	let totalPrice = 0;

	// Subscribe to changes in the cart to update the count
	Cart.subscribe(items => {
		totalPrice = items.reduce((total, item) => total + item.quantity * item.price, 0);
	});
</script>

<div class="flex h-full flex-col overflow-y-scroll bg-white shadow-xl">
	<div class="flex-1 overflow-y-auto px-4 py-6 sm:px-6">
		<div class="flex items-start justify-between">
			<h2 class="text-lg font-medium text-gray-900 mb-2" id="slide-over-title">Shopping cart</h2>
			<div class="ml-3 flex h-7 items-center">
				<button type="button" class="relative -m-2 p-2 text-gray-400 hover:text-gray-500">
					<span class="absolute -inset-0.5"></span>
					<span class="sr-only">Close panel</span>
					<svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor"
							 aria-hidden="true">
						<path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12"/>
					</svg>
				</button>
			</div>
		</div>
		{#if $Cart.length === 0}
			<div class="mt-4">
				<div class="flex justify-center">
					<p class="text-gray-500 text-lg">Your cart is empty</p>
				</div>
			</div>
		{:else}
			<div class="mt-4">
				<div class="flow-root">
					<ul role="list" class="-my-6 divide-y divide-gray-200">
						{#each $Cart as item }
							<li class="flex py-6">
								<div class="h-40 w-40 flex-shrink-0 overflow-hidden rounded-md border border-gray-200">
									<img src={`${BACKEND_URL}/static/products/${item.image}`} alt={item.name}/>
								</div>

								<div class="ml-4 flex flex-1 flex-col">
									<div>
										<div
											class="flex justify-between text-base font-medium text-gray-900">
											<h3>
												<a href="/product_details/{item.id}">{item.name}</a>
											</h3>
											<p class="ml-4">{item.price * item.quantity} €</p>
										</div>
										<p class="mt-1 text-sm text-gray-500">{item.category}</p>
									</div>
									<div class="flex flex-1 items-end justify-between text-sm">
										<p class="text-gray-500">Qty {item.quantity}</p>

										<div class="flex">
											<button type="button"
															class="font-medium text-indigo-600 hover:text-indigo-500"
															on:click={() => Cart.remove(item.id)}>Remove
											</button>
										</div>
									</div>
								</div>
							</li>
						{/each}
					</ul>
				</div>
			</div>
		{/if}
	</div>
	<div class="border-t border-gray-200 px-4 py-6 sm:px-6">
		<div class="flex justify-between text-base font-medium text-gray-900">
			<p>Subtotal</p>
			<p>{totalPrice} €</p>
		</div>
		<p class="mt-0.5 text-sm text-gray-500">Shipping and taxes calculated at checkout.</p>
		<div class="mt-6">
			<button type="button"
					class="w-full flex items-center justify-center rounded-md border border-transparent px-6 py-3 text-base font-medium text-white shadow-sm
               { $Cart.length === 0 ? 'bg-gray-400 cursor-not-allowed' : 'bg-indigo-600 hover:bg-indigo-700'}"
					on:click={() => goto('/checkout')}
					disabled={$Cart.length === 0}>
				Checkout
			</button>
		</div>
		<div class="mt-6 flex justify-center text-center text-sm text-gray-500">
			<p>
				or
				<button type="button" class="font-medium text-indigo-600 hover:text-indigo-500"
								on:click={() => goto('/')}>
					Continue Shopping
					<span aria-hidden="true"> &rarr;</span>
				</button>
			</p>
		</div>
	</div>
</div>


