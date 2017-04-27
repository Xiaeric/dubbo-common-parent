/**
 * Created by yyx on 2017/3/29.
 * �û�ҵ���
 * $http.get
 *
 * $http.head
 *
 * $http.post

 * $http.put

 * $http.delete

 * $http.jsonp

 * $http.patch
 */
app.service('complainListService', ['$http', '$q', 'host',function($http, $q, host) {
    return {
        // ��ҳ�б�
        listPage:function(params) {
            var deferred = $q.defer();
            // $http�����᷵��һ��promise����
            var promise = $http({
                method: "post",
                data: angular.toJson(params),//JsonData = {"id":1,"value":"hello"}
                url: host+"/listUser"
            }).success(function (d) {
                deferred.resolve(d);
            });
            /*
             * ��promise��then�����������������������ֱ���promise��ִ�е�ʱ���Լ�promise���ܾ���ʱ����Ҫ���еĲ���
             * Ҳ����ʹ��success��error�ص����棬��promise.success(function(data, status, headers, config){});promise.error(function(data, status, headers, config){});
             * then()�������յ�resp����Ӧ���󣩰���5�����ԣ���
             *	1. data���ַ�������󣩣���Ӧ��
             *	2. status:��Ӧhttp��״̬��,��200
             *	3. headers(����)��ͷ��Ϣ��getter���������Խ���һ��������������ȡ��Ӧ���ֵ�ֵ
             *	4. config(����)������ԭʼ������������ö���
             *	5. statusText:��Ӧ��http״̬�ı�����"ok"
             */
            /*
             promise.then(
             // ͨѶ�ɹ��Ĵ�����
             function(answer){
             //��������ԶԷ��ص����ݼ���һ���Ĵ���,�ٽ���controller���д���
             deferred.resolve(answer.data);
             },
             // ͨѶʧ�ܵĴ�����
             function(error){
             // �����ȶ�ʧ�ܵ����ݼ��������ٽ���controller���д���
             // �ڴ�֮ǰ�Ѿ���httpInterceptorͳһ������󣬴˴���������ʡ�ԣ��˴�ֻ��չʾ
             console.dir("error:"+error);
             deferred.reject(error);
             });

             //����promise���󣬽���controller��������ɹ���ʧ�ܵ�ҵ��ص�
             */
            return deferred.promise;
        },
        // �༭�û�
        editUser:function(params) {
            var deferred = $q.defer();
            $http({
                method: "put",
                data: angular.toJson(params),//JsonData = {"id":1,"value":"hello"}
                url: host+"/user/"+user.id,
                headers: { "Content-Type": "application/json" }
            }).success(function (d) {
                deferred.resolve(d);
            });
            return deferred.promise;
        },
        // ɾ���û�
        delUser:function(id) {
            var deferred = $q.defer();
            $http({
                method: "delete",
                url: host+"/user/"+id,
            }).success(function (d) {
                deferred.resolve(d);
            });
            return deferred.promise;
        }
    }
}]);