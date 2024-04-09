<script lang="ts">
    import { Cart } from '$lib/cart';
    import {BACKEND_URL} from "../../../config";

    export let data;
    const { product } = data

    function getShippingDate() {
        // Get the current date
        const currentDate = new Date();

        // Add 3 days to the current date
        currentDate.setDate(currentDate.getDate() + 3);

        // Check if the shipping date falls on a weekend (Saturday or Sunday)
        const dayOfWeek = currentDate.getDay();
        if (dayOfWeek === 0 || dayOfWeek === 6) {
            // If the shipping date falls on a weekend, adjust it to the next working day (Monday)
            currentDate.setDate(currentDate.getDate() + (dayOfWeek === 0 ? 1 : 2));
        }

        // Extract date components (year, month, day)
        const weekday = currentDate.toLocaleString('default', { weekday: 'long' });
        const month = currentDate.toLocaleString('default', { month: 'long' });
        const day = String(currentDate.getDate()).padStart(2, '0');

        // Format the date as YYYY-MM-DD
        const formattedDate = `${weekday} ${day}.${month}`;

        // Return the formatted shipping date
        return formattedDate;
    }
</script>

<div>
    {#if product}
        <h2 class="text-3xl font-bold tracking-tight text-gray-900 sm:text-4xl text-center sm:py-12">{product.name}</h2>
        <div class="mx-auto grid max-w-2xl grid-cols-1 items-center gap-x-8 gap-y-16 px-4 sm:px-6 sm:py-12 lg:max-w-7xl lg:grid-cols-2 lg:px-8">
            <div>
                <h2 class="text-gray-900 font-bold tracking-tight sm:text-3xl">Category: {product.category}</h2>
                <p class="mt-4 text-gray-500">{product.description}</p>
                <dl class="mt-16 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 sm:gap-y-16 lg:gap-x-8">
                    {#if Object.keys(product.attributes).length > 0}
                        {#each Object.entries(product.attributes) as [key, value]}
                            <div class="border-t border-gray-200 pt-4">
                                <dt class="font-medium text-gray-900">{key}</dt>
                                <dd class="mt-2 text-sm text-gray-500">{value}</dd>
                            </div>
                        {/each}
                    {/if}
                </dl>
            </div>
            <div>
                <div class="aspect-w-1 aspect-h-1 rounded-lg overflow-hidden">
                    <img src={BACKEND_URL}/static/products/{product.image} alt={product.name} />
                </div>
                <div class="mt-8">
                    <p class="font-bold tracking-tight text-gray-900 sm:text-3xl">{product.price} €</p>
                    <div class="mt-4">
                        <p class="text-sm font-medium text-gray-900">Free Shipping till</p>
                        <p class="mt-1 text-sm text-gray-500">{getShippingDate()}</p>
                    </div>
                    <p class="mt-4 text-gray-500">Stock: {product.stock}</p>
                    <button on:click={() => Cart.add({...product, quantity: 1})} class="mt-8 bg-indigo-600 w-full inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-white hover:bg-indigo-700">
                        Add to cart
                    </button>
                </div>
            </div>
        </div>
    {/if}
</div>
