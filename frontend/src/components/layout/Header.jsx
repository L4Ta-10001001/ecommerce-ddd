import { useEffect, useState } from 'react'
import { API_BASE_URL, AUTH_HEADERS, LABELS } from '../../constants'

const STATUS_CHECK_INTERVAL = 5000

const Header = () => {
  const [isBackendOnline, setIsBackendOnline] = useState(false)

  useEffect(() => {
    const checkBackend = async () => {
      try {
        const response = await fetch(`${API_BASE_URL}/api/orders/1`, {
          headers: AUTH_HEADERS,
        })
        setIsBackendOnline(response.ok || response.status === 404)
      } catch {
        setIsBackendOnline(false)
      }
    }

    checkBackend()
    const interval = setInterval(checkBackend, STATUS_CHECK_INTERVAL)

    return () => clearInterval(interval)
  }, [])

  const statusColor = isBackendOnline ? 'bg-emerald-400' : 'bg-rose-400'

  return (
    <header className="flex flex-wrap items-center justify-between gap-4 rounded-2xl border border-slate-700 bg-slate-800/60 px-6 py-4 shadow-lg">
      <div className="flex items-center gap-4">
        <h1 className="text-3xl font-bold text-white tracking-wide">{LABELS.appTitle}</h1>
        <span className="rounded-full bg-indigo-500/20 px-4 py-1 text-sm font-semibold text-indigo-200">
          {LABELS.architectureBadge}
        </span>
      </div>

      <div className="flex items-center gap-3 text-sm text-slate-300">
        <span className={`h-3 w-3 rounded-full ${statusColor}`} />
        <span className="uppercase tracking-wider">{LABELS.backendLabel}</span>
      </div>
    </header>
  )
}

export default Header
