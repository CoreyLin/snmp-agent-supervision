package cn.chengdu.isdk.neo.snmpagentsupervision.service;

import cn.chengdu.isdk.neo.snmpagentsupervision.entity.ActivationInfo;
import cn.chengdu.isdk.neo.snmpagentsupervision.entity.SupervisionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class SnmpAgentSupervision {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @RequestMapping("/v1/agents/{agent_id}/supervision_result")
    public SupervisionResult getSnmpgetResult(@PathVariable("agent_id") String agentId) {
        String snmpAgentActivatorServiceUrl = environment.getProperty("SNMP_AGENT_ACTIVATOR_SERVICE", "127.0.0.1");
        System.out.println(snmpAgentActivatorServiceUrl);
        SupervisionResult supervisionResult = new SupervisionResult();
        ActivationInfo activationInfo = restTemplate.getForObject("http://" + snmpAgentActivatorServiceUrl + ":8080/v1/activated_snmp_agents/" + agentId, ActivationInfo.class);
        if (activationInfo.isStatus()) {
            Random random = new Random();
            boolean b = random.nextBoolean();
            supervisionResult.setSnmpgetStatus(b);
        } else {
            supervisionResult.setSnmpgetStatus(false);
        }
        return supervisionResult;
    }
}
