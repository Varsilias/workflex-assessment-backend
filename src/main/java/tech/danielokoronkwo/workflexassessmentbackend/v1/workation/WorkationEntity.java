package tech.danielokoronkwo.workflexassessmentbackend.v1.workation;

import jakarta.persistence.*;
import tech.danielokoronkwo.workflexassessmentbackend.common.constants.WorkationRisk;
import tech.danielokoronkwo.workflexassessmentbackend.common.entities.BaseEntity;

import java.util.Date;

@Entity
@Table(name = "workations")
public class WorkationEntity extends BaseEntity {
    @Column(name = "workation_id")
    private String workationId;

    @Column(name = "employee")
    private String employee;

    @Column(name = "origin")
    private String origin;

    @Column(name = "destination")
    private String destination;

    @Column(name = "working_days")
    private int workingDays;

    @Column(name = "risk")
    @Enumerated(EnumType.STRING)
    private WorkationRisk risk;

    @Column(name = "start_date")
    private Date start;

    @Column(name = "end_date")
    private Date end;

    public WorkationEntity() {
    }

    public WorkationEntity(Long id, String publicId, Date createdAt, Date updatedAt, String workationId, String employee, String origin, String destination, int workingDays, WorkationRisk risk, Date start, Date end) {
        super(id, publicId, createdAt, updatedAt);
        this.workationId = workationId;
        this.employee = employee;
        this.origin = origin;
        this.destination = destination;
        this.workingDays = workingDays;
        this.risk = risk;
        this.start = start;
        this.end = end;
    }

    public String getWorkationId() {
        return workationId;
    }

    public void setWorkationId(String workationId) {
        this.workationId = workationId;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public WorkationRisk getRisk() {
        return risk;
    }

    public void setRisk(WorkationRisk risk) {
        this.risk = risk;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
