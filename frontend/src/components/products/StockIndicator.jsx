const StockIndicator = ({ currentStock, maxStock }) => {
  const ratio = maxStock === 0 ? 0 : currentStock / maxStock
  const percentage = Math.max(0, Math.min(100, Math.round(ratio * 100)))

  const colorClass = ratio > 0.6
    ? 'bg-emerald-500'
    : ratio > 0.3
      ? 'bg-amber-500'
      : 'bg-rose-500'

  return (
    <div className="w-full">
      <div className="h-2 w-full overflow-hidden rounded-full bg-slate-700">
        <div
          className={`h-full ${colorClass} transition-all duration-500`}
          style={{ width: `${percentage}%` }}
        />
      </div>
    </div>
  )
}

export default StockIndicator
