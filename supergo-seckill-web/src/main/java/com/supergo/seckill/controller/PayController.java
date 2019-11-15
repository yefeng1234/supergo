package com.supergo.seckill.controller;

import com.supergo.feign.ApiWeixinPayFeign;
import com.supergo.feign.ApiSeckillOrderFeign;
import com.supergo.http.HttpResult;
import com.supergo.common.pojo.SeckillOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/***
 *
 * @Author:shenkunlin
 * @Description:itheima
 * @date: 2019/3/26 16:19
 *
 ****/
@RestController
@RequestMapping(value = "/pay")
public class PayController {

    @Autowired
    private ApiWeixinPayFeign apiWeixinPayFeign;

    @Autowired
    private ApiSeckillOrderFeign apiSeckillOrderFeign;

    /***
     * 查询支付状态
     * @param outtradeno:商户订单号
     */
    @RequestMapping(value = "/queryStatus/{outtradeno}")
    public HttpResult queryStatus(@PathVariable("outtradeno") String outtradeno){
        //获取用户登录名
        String userId = "123";
        try {
            //累计=5
            int count=0;
            while (true){
                //查询支付状态
                Map<String, String> resultMap = (Map<String, String>)apiWeixinPayFeign.queryStatus(outtradeno).getData();
                System.out.println(resultMap);
                //获取业务结果  result_code:SUCCESS  成功
                //判断交易状态   trade_state:SUCCESS 交易成功
                //                          REFUND—转入退款  证书
                //                          NOTPAY—未支付
                //                          CLOSED—已关闭
                //                          REVOKED—已撤销（付款码支付）
                //                          USERPAYING--用户支付中（付款码支付）
                //                          PAYERROR--支付失败(其他原因，如银行返回失败)
                String tradeState = resultMap.get("trade_state");
                //SUCCESS:支付成功   跳转到支付成功页面
                if(tradeState.equalsIgnoreCase("SUCCESS")){
                    //修改订单状态   transaction_id
                    //String userid,String transactionId,String payTime  time_end
                    String transactionId = resultMap.get("transaction_id");
                    String payTime = resultMap.get("time_end");
                    apiSeckillOrderFeign.updateStatus(userId,transactionId,payTime);
                    return HttpResult.ok("支付成功");
                }else if(tradeState.equalsIgnoreCase("NOTPAY") || tradeState.equalsIgnoreCase("USERPAYING")){
                    //NOTPAY|USERPAYING  继续查询
                    System.out.println("继续查询....");
                    //休眠3秒
                    Thread.sleep(3000);
                    //累加
                    count++;
                    //如果count>=5,超过15秒
                    if(count>=5){
                        //超时,关闭订单，再删除订单
                        Map<String, String> closeResultMap = (Map<String, String>)apiWeixinPayFeign.closePay(outtradeno).getData();
                        if(closeResultMap.get("return_code").equalsIgnoreCase("SUCCESS")){
                            //判断业务结果
                            String resultCode = closeResultMap.get("result_code");
                            //SUCCESS：关闭成功
                            if(resultCode.equalsIgnoreCase("SUCCESS")){
                                //删除订单
                                apiSeckillOrderFeign.deleteOrder(userId);
                            }else if(resultCode.equalsIgnoreCase("FAIL")){
                                //FAIL:关闭失败
                                    //ORDERPAID:已支付,修改订单状态
                                    if(resultCode.equalsIgnoreCase("ORDERPAID")){
                                        //查询支付状态
                                        resultMap = (Map<String, String>)apiWeixinPayFeign.queryStatus(outtradeno).getData();
                                        //修改订单状态
                                        String transactionId = resultMap.get("transaction_id");
                                        String payTime = resultMap.get("time_end");
                                        apiSeckillOrderFeign.updateStatus(userId,transactionId,payTime);
                                        return HttpResult.ok("支付成功！");
                                    }else if(resultCode.equalsIgnoreCase("ORDERCLOSED")){
                                        //ORDERCLOSED:删除订单
                                        apiSeckillOrderFeign.deleteOrder(userId);
                                    }
                            }else{
                                //其他错误
                            }
                        }
                        return HttpResult.ok("504");
                    }
                    continue;
                }else{
                    //其他支付失败
                    return HttpResult.error("支付失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }

    /***
     * 创建二维码
     * @return
     */
    @RequestMapping(value = "/create/native")
    public HttpResult createNative(){
        //根据用户名字，查询用户的订单信息
        String userName = "123";
        //根据订单信息创建二维码
        SeckillOrder seckillOrder = (SeckillOrder)apiSeckillOrderFeign.getOrderByUserName(userName).getData();
        //return weixinPayService.createNative(String.valueOf(seckillOrder.getId()),String.valueOf(seckillOrder.getMoney()));
        return apiWeixinPayFeign.createNative(String.valueOf(seckillOrder.getId()),"1");
    }
}
