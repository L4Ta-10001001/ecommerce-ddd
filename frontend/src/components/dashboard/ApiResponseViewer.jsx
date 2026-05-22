import { useState } from 'react'
import { LABELS } from '../../constants'

const ApiResponseViewer = ({ data }) => {
  const [isOpen, setIsOpen] = useState(false)

  const toggleOpen = () => setIsOpen((current) => !current)

  return (
    <section className="rounded-2xl border border-slate-700 bg-slate-900/60 p-5 shadow-lg">
      <button
        type="button"
        className="flex w-full items-center justify-between text-left text-sm font-semibold text-indigo-200"
        onClick={toggleOpen}
      >
        <span>{LABELS.apiResponseTitle}</span>
        <span className="text-xs text-slate-400">{isOpen ? LABELS.apiResponseHide : LABELS.apiResponseShow}</span>
      </button>

      {isOpen && (
        <pre className="mt-4 max-h-64 overflow-auto rounded-xl border border-slate-700 bg-slate-950 p-4 text-xs text-emerald-200">
          {JSON.stringify(data ?? { info: LABELS.apiResponseEmpty }, null, 2)}
        </pre>
      )}
    </section>
  )
}

export default ApiResponseViewer
