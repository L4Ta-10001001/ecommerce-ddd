import { ORDER_STATUS } from '../../constants'

const STATUS_STYLES = {
  [ORDER_STATUS.CONFIRMED]: 'bg-emerald-500/20 text-emerald-300 border-emerald-500/40',
  [ORDER_STATUS.CANCELLED]: 'bg-rose-500/20 text-rose-300 border-rose-500/40',
  [ORDER_STATUS.PENDING]: 'bg-amber-500/20 text-amber-300 border-amber-500/40',
}

const DEFAULT_STATUS_STYLE = 'bg-slate-700 text-slate-200 border-slate-600'

const StatusBadge = ({ status }) => {
  const badgeStyle = STATUS_STYLES[status] || DEFAULT_STATUS_STYLE

  return (
    <span className={`inline-flex items-center rounded-full border px-3 py-1 text-xs font-semibold uppercase tracking-wide ${badgeStyle}`}>
      {status}
    </span>
  )
}

export default StatusBadge
