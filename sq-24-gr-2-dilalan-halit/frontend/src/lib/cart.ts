import { writable } from "svelte/store";
import type { Product } from './Product';

type CartItem = Omit<Product, "stock"> & { quantity: number };

export const Cart = (() => {
    if (import.meta.env.SSR) {
        // For SSR, return an empty store
        return writable<CartItem[]>([]);
    }

    const session: CartItem[] = JSON.parse(sessionStorage.getItem("cart") || "[]");

    const { subscribe, update } = writable<CartItem[]>(session);

    subscribe((items) => {
        sessionStorage.setItem("cart", JSON.stringify(items));
    });

    return {
        subscribe,
        add: (item: CartItem) => {
            update((store) => {
                const existingItemIndex = store.findIndex((i) => i.id === item.id);
                if (existingItemIndex !== -1) {
                    // If item already exists in the cart, increase the quantity by 1
                    store[existingItemIndex].quantity++;
                    return [...store];
                } else {
                    // If item doesn't exist in the cart, add it with quantity 1
                    return [...store, { ...item, quantity: 1 }];
                }
            });
        },
        remove: (id: number) => {
            update((store) => store.filter((i) => i.id !== id));
        },
        clear: () => {
            update((store) => []);
        }
    };
})();

