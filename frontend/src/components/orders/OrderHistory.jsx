import StatusBadge from '../layout/StatusBadge'
import { ORDER_STATUS } from '../../constants'

const OrderHistory = ({ orders, onCancel }) => {
  return (
    <section className="rounded-2xl border border-slate-700 bg-slate-800/60 p-5 shadow-lg">
      <div className="flex items-center justify-between">
        <h2 className="text-xl font-semibold text-white">Order History</h2>
        <span className="text-xs text-slate-400">session only</span>
      </div>

      <div className="mt-4 space-y-3">
        {orders.length === 0 ? (
          <p className="text-sm text-slate-400">No orders yet. Submit one to see it here.</p>
        ) : (
          orders.map((order) => (
            <div
              key={order.orderId}
              className="flex flex-wrap items-center justify-between gap-4 rounded-xl border border-slate-700 bg-slate-900/60 px-4 py-3"
            >
              <div>
                <p className="text-sm text-slate-300">Order ID</p>
                <p className="text-sm font-semibold text-white">{order.orderId}</p>
              </div>

              <div>
                <p className="text-sm text-slate-300">Item</p>
                <p className="text-sm font-semibold text-white">
                  {order.items?.[0]?.productName} × {order.items?.[0]?.quantity}
                </p>
              </div>

              <div>
                <p className="text-sm text-slate-300">Total</p>
                <p className="text-sm font-semibold text-white">${order.total.toFixed(2)}</p>
              </div>

              <StatusBadge status={order.status} />

              {order.status === ORDER_STATUS.CONFIRMED && (
                <button
                  type="button"
                  className="rounded-xl border border-rose-500/40 bg-rose-500/10 px-3 py-2 text-xs font-semibold text-rose-200 transition hover:bg-rose-500/20"
                  onClick={() => onCancel(order)}
                >
                  Cancel
                </button>
              )}
            </div>
          ))
        )}
      </div>
    </section>
  )
}

export default OrderHistory
