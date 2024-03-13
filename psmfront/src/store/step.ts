import { defineStore } from "pinia";

export const useStepStore = defineStore('step', {
    state: () => ({
        currentStep: 1,
    }),
    actions: {
        nextStep() {
            this.currentStep++;
        },
        prevStep() {
            this.currentStep--;
        },
    },
})