export type ProductDetails = {
    id: number;
    name: string;
    description: string;
    price: number;
    stock: number;
    category: string;
    image: string;
    attributes: Map<string, string>;
}