import { LABELS } from '../../constants'

const DomainRulePanel = ({ lastError }) => {
  const message = lastError ? LABELS.domainRuleError : LABELS.domainRuleSuccess
  const panelStyle = lastError
    ? 'border-rose-500/40 bg-rose-500/10 text-rose-200'
    : 'border-emerald-500/40 bg-emerald-500/10 text-emerald-200'

  return (
    <section className={`rounded-2xl border p-5 shadow-lg ${panelStyle}`}>
      <h3 className="text-sm font-semibold uppercase tracking-widest">Domain Rule</h3>
      <p className="mt-2 text-sm leading-relaxed text-white">{message}</p>
    </section>
  )
}

export default DomainRulePanel
