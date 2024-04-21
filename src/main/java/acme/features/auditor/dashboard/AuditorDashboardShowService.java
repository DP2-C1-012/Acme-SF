
package acme.features.auditor.dashboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.auditRecord.AuditRecord;
import acme.entities.codeAudit.CodeAudit;
import acme.entities.codeAudit.Type;
import acme.forms.AuditorDashboard;
import acme.roles.Auditor;

@Service
public class AuditorDashboardShowService extends AbstractService<Auditor, AuditorDashboard> {

	@Autowired
	protected AuditorDahsbaordresopitory repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int auditorID;
		int minARinCA;
		int maxARinCA;
		double minDur;
		double maxDur;
		double avgDur;
		double devDur;
		double avgARinCA;
		double deviationAR;
		Collection<CodeAudit> codeAudits;
		Collection<AuditRecord> recors;
		AuditorDashboard dahsbaord;
		auditorID = super.getRequest().getPrincipal().getActiveRoleId();
		dahsbaord = new AuditorDashboard();
		codeAudits = this.repository.findAuditorCodeAuditsByAuditorID(auditorID);
		recors = this.repository.findAuditorAuditRecorsByAuditorID(auditorID);
		int totalDynamic = (int) codeAudits.stream().filter(ca -> ca.getType() == Type.Dynamic).count();
		int totalStatic = (int) codeAudits.stream().filter(ca -> ca.getType() == Type.Static).count();
		Map<CodeAudit, Long> minMaxCA = recors.stream().map(ar -> ar.getCodeAudit()).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		minARinCA = Integer.MAX_VALUE;
		maxARinCA = 0;
		int k = 0;
		int total = 0;
		List<Integer> numsAR = new ArrayList<Integer>();
		for (Map.Entry<CodeAudit, Long> entry : minMaxCA.entrySet()) {
			k++;
			numsAR.add(entry.getValue().intValue());
			if (minARinCA > entry.getValue())
				minARinCA = entry.getValue().intValue();
			if (maxARinCA < entry.getValue())
				maxARinCA = entry.getValue().intValue();
			total = total + entry.getValue().intValue();
		}
		avgARinCA = (double) total / k;
		double sumatory = 0.;
		for (Integer num : numsAR)
			sumatory = sumatory + Math.pow(num - avgARinCA, 2);
		List<Double> duraciones = recors.stream().map(ar -> (int) MomentHelper.computeDuration(ar.getPeriodStart(), ar.getPeriodEnd()).abs().getSeconds() / 3600.).collect(Collectors.toList());
		double totalDur = 0;
		minDur = Integer.MAX_VALUE;
		maxDur = 0;
		for (double dur : duraciones) {
			totalDur = totalDur + dur;
			minDur = minDur > dur ? dur : minDur;
			maxDur = maxDur < dur ? dur : maxDur;
		}
		avgDur = totalDur / duraciones.size();
		double sumDur = duraciones.stream().map(d -> Math.pow(d - avgDur, 2)).collect(Collectors.summingDouble(Double::doubleValue));
		deviationAR = Math.sqrt(sumatory / k);
		devDur = Math.sqrt(sumDur / duraciones.size());

		dahsbaord.setDeviationNumAuditRecords(deviationAR);
		dahsbaord.setAverageNumAuditRecords(avgARinCA);
		dahsbaord.setMaxAuditRecords(maxARinCA);
		dahsbaord.setMinAuditRecords(minARinCA);
		dahsbaord.setTotalDynamicCodeAudits(totalDynamic);
		dahsbaord.setTotalStaticCodeAudits(totalStatic);
		dahsbaord.setMaxPeriodLength(maxDur);
		dahsbaord.setMinPeriodLength(minDur);
		dahsbaord.setAverageNumPeriodLength(avgDur);
		dahsbaord.setDeviationNumPeriodLength(devDur);

		super.getBuffer().addData(dahsbaord);
	}
	@Override
	public void unbind(final AuditorDashboard object) {
		Dataset dataset;
		dataset = super.unbind(object, "totalDynamicCodeAudits", "totalStaticCodeAudits", "minAuditRecords", "maxAuditRecords", "averageNumAuditRecords", "deviationNumAuditRecords", "minPeriodLength", "maxPeriodLength", "averageNumPeriodLength",
			"deviationNumPeriodLength");
		super.getResponse().addData(dataset);
	}
}
