package org.jetlinks.rule.engine.cluster.worker;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetlinks.rule.engine.api.scheduler.ScheduleJob;
import org.jetlinks.rule.engine.api.worker.Worker;
import org.jetlinks.rule.engine.api.codec.Codec;
import org.jetlinks.rule.engine.api.codec.Codecs;
import org.jetlinks.rule.engine.api.rpc.RpcDefinition;
import org.jetlinks.rule.engine.cluster.ClusterConstants;

import java.util.List;

public interface WorkerRpc {

    static RpcDefinition<CreateTaskRequest, CreateTaskResponse> createTask(String workerId) {
        return RpcDefinition.of(
                ClusterConstants.Topics.createTask(workerId),
                CreateTaskRequest.class,
                CreateTaskResponse.class
        );
    }

    static RpcDefinition<Void, List<String>> getSupportExecutors(String workerId) {
        return RpcDefinition.of(
                ClusterConstants.Topics.getWorkerSupportExecutors(workerId),
                Codec.voidCodec(),
                Codecs.lookupForList(String.class)
        );
    }

    static RpcDefinition<Void, Worker.State> getWorkerState(String workerId) {
        return RpcDefinition.ofNoParameter(
                ClusterConstants.Topics.getWorkerState(workerId),
                Worker.State.class
        );
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class CreateTaskRequest{
        String schedulerId;
        ScheduleJob job;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class CreateTaskResponse {
        private String taskId;
        private String taskName;
    }

}