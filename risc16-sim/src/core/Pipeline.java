package core;

import instruction.Instruction;

public class Pipeline {
    public Short fetch;
    public Instruction decodedInstruction;

    public Pipeline(){
        this.fetch = null;
        this.decodedInstruction = null;
    }

    public void copyFrom(Pipeline nextPipeline) {
        this.fetch = nextPipeline.fetch;
        this.decodedInstruction = nextPipeline.decodedInstruction;
    }

    public void clear() {
        fetch = null;
        decodedInstruction = null;
    }

    public void setFetch(Short fetch) {
        this.fetch = fetch;
    }

    public void setDecodedInstruction(Instruction decodedInstruction) {
        this.decodedInstruction = decodedInstruction;
    }

    @Override
    public String toString() {
        return "Pipeline {" +
                "fetch=" + fetch +
                ", decode=" + decodedInstruction +
                '}';
    }
}
