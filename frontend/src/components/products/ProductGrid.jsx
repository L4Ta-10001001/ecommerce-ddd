import ProductCard from './ProductCard'

const ProductGrid = ({ products }) => {
  return (
    <section className="rounded-2xl border border-slate-700 bg-slate-900/40 p-5 shadow-lg">
      <h2 className="mb-4 text-xl font-semibold text-white">Products & Stock</h2>
      <div className="grid gap-4 sm:grid-cols-2 xl:grid-cols-1">
        {products.map((product) => (
          <ProductCard key={product.id} product={product} />
        ))}
      </div>
    </section>
  )
}

export default ProductGrid
