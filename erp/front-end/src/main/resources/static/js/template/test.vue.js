Vue.component('Hello', {
    props: ['name','num'],
    template: '<div  @click="say">Hello {{name}}，{{num+myNum}}次！</div>',
    data:function(){
        return {
            myNum:0
        }
    },
    methods: {
        say:function(){
            this.myNum ++ ;
        }
    }
});