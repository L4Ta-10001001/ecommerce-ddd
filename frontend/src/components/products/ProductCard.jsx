import StockIndicator from './StockIndicator'

const ProductCard = ({ product }) => {
  const formattedPrice = product.price.toLocaleString('en-US', {
    style: 'currency',
    currency: 'USD',
  })

  return (
    <article className="rounded-2xl border border-slate-700 bg-slate-800/70 p-4 shadow-lg">
      <div className="flex items-center justify-between">
        <h3 className="text-lg font-semibold text-white">{product.name}</h3>
        <span className="text-sm font-semibold text-indigo-300">{formattedPrice}</span>
      </div>

      <div className="mt-4 space-y-2">
        <StockIndicator currentStock={product.stock} maxStock={product.maxStock} />
        <div className="flex items-center justify-between text-sm text-slate-300">
          <span>{product.stock} units available</span>
          <span className="text-xs uppercase tracking-widest text-slate-400">stock</span>
        </div>
      </div>
    </article>
  )
}

export default ProductCard
