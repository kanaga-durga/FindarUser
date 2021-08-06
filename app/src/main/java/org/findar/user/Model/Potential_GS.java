package org.findar.user.Model;

import java.util.List;

public class Potential_GS {
    String job_id;
    String services;
    String problems;
    String subproblems;
    List<Companies> companiesList;


    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public String getSubproblems() {
        return subproblems;
    }

    public void setSubproblems(String subproblems) {
        this.subproblems = subproblems;
    }

    public List<Companies> getCompaniesList() {
        return companiesList;
    }

    public void setCompaniesList(List<Companies> companiesList) {
        this.companiesList = companiesList;
    }
}
