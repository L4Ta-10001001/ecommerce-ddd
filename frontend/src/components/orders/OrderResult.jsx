import StatusBadge from '../layout/StatusBadge'
import { LABELS } from '../../constants'

const OrderResult = ({ order, error }) => {
  if (!order && !error) {
    return (
      <section className="rounded-2xl border border-dashed border-slate-700 bg-slate-900/40 p-5 text-sm text-slate-400">
        {LABELS.orderResultEmpty}
      </section>
    )
  }

  if (error) {
    return (
      <section className="rounded-2xl border border-rose-500/40 bg-rose-500/10 p-5 shadow-lg">
        <p className="text-sm font-semibold text-rose-300">{LABELS.domainViolation}</p>
        <p className="mt-2 text-base text-white">{error.error}</p>
      </section>
    )
  }

  return (
    <section className="rounded-2xl border border-emerald-500/40 bg-emerald-500/10 p-5 shadow-lg">
      <div className="flex items-center justify-between">
        <StatusBadge status={order.status} />
        <span className="text-sm text-emerald-200">{LABELS.orderConfirmedLabel}</span>
      </div>
      <div className="mt-3 space-y-1 text-sm text-slate-200">
        <p>{LABELS.orderIdLabel}: <span className="font-semibold text-white">{order.orderId}</span></p>
        <p>{LABELS.orderTotalLabel}: <span className="font-semibold text-white">${order.total.toFixed(2)}</span></p>
      </div>
    </section>
  )
}

export default OrderResult
