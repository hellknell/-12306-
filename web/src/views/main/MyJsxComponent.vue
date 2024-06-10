<script lang="jsx">
import { ref,defineComponent } from 'vue'
export default defineComponent({
  setup(){
    const count = ref(100);
    const getjsx = ()=>{
      count.value += 1
    }

    return () => (
        <div>
          <a-button onClick={getjsx}>加一</a-button>
          <div>{count.value}</div>
        </div>
    )
  },
})
</script>