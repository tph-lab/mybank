package com.yc.mybank.controller;

import com.yc.mybank.bean.Accounts;
import com.yc.mybank.bean.OpTypes;
import com.yc.mybank.biz.AccountService;
import com.yc.mybank.vo.AccountVO;
import com.yc.mybank.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Slf4j
@Controller
@Api(value = "账户操作接口", tags = {"账户操作接口", "控制层"})
public class AccountsController {

    @Autowired
    private AccountService accountService;


    /**
     * 开户
     * @param accountVO
     * @return
     */
    @RequestMapping(value = "openAccounts",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody ResultVO openAccounts(AccountVO accountVO){
        log.debug("用户请求开户，存入"+accountVO.getMoney());
        ResultVO rv=new ResultVO();
        try {
            Accounts a=new Accounts();
            double money=1;
            if(accountVO.getMoney()!=null&&accountVO.getMoney()>0){
                money=accountVO.getMoney();
            }
            Integer id=accountService.openAccount(a,money);
            a.setAccountId(id);
            a.setBalance(money);
            rv.setCode(1);
            rv.setData(a);
            rv.setMsg("成功！！！");
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg("失败！！！");
        }

       return rv;
    }


    /**
     * 存钱
     * @param accountVO
     * @return
     */
    @RequestMapping(value = "deposite",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody ResultVO deposite(AccountVO accountVO){
        log.debug("用户，存入"+accountVO.getMoney());
        ResultVO rv=new ResultVO();
        try {
            Accounts a=new Accounts();
            double money=0;
            if(accountVO.getMoney()!=null&&accountVO.getMoney()>0){
                money=accountVO.getMoney();
            }
            a.setAccountId(accountVO.getAccountId());
            Accounts aa=accountService.deposite(a,money, OpTypes.deposite.getName(),null);
            rv.setCode(1);
            rv.setMsg("成功！！！");
            rv.setData(aa);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg("失败！！！");
        }

        return rv;
    }


    /**
     * 取钱
     * @param accountVO
     * @return
     */
    @RequestMapping(value = "withdraw",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody ResultVO withdraw(AccountVO accountVO){
        log.debug("用户，取钱"+accountVO.getMoney());
        ResultVO rv=new ResultVO();
        try {
            Accounts a=new Accounts();
            double money=0;

            if(accountVO.getMoney()!=null&&accountVO.getMoney()>0){
                money=accountVO.getMoney();
            }
            a.setAccountId(accountVO.getAccountId());
            Accounts aa=accountService.withdraw(a,money, OpTypes.withdraw.getName(),null);
            rv.setCode(1);
            rv.setMsg("成功！！！");
            rv.setData(aa);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg("失败！！！");
        }

        return rv;

    }

    /**
     * 转账
     * @param accountVO
     * @return private Integer accountId;
     *     private Double money;
     *     private Integer inAccountId;
     */
    @ApiOperation(value = "转账", notes = "根据账户编号及金额, 对方接收账号来完成转账操作，注意此时的金额表示要取的金额")
    @ApiImplicitParams({@ApiImplicitParam(name = "accountId", value = "自己账户编号", required = true, dataType = "Int"),
            @ApiImplicitParam(name = "money", value = "转账金额", required = true, dataType = "Double"),
            @ApiImplicitParam(name = "inAccountId", value = "对方接收账号", required = true, dataType = "Int")})
    @RequestMapping(value = "transfer",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody ResultVO transfer(AccountVO accountVO){
        log.debug("用户转账"+accountVO.getMoney());
        ResultVO rv=new ResultVO();
        try {
            Accounts inAccount=new Accounts();
            Accounts outAccount=new Accounts();
            double money=0;
            if(accountVO.getMoney()!=null&&accountVO.getMoney()>0){
                money=accountVO.getMoney();
            }
            outAccount.setAccountId(accountVO.getAccountId());

            inAccount.setAccountId(accountVO.getInAccountId());
            //public Accounts transfer(Accounts inAccount, Accounts outAccount, double money) {
            Accounts aa=accountService.transfer(inAccount,outAccount,money);
            rv.setCode(1);
            rv.setMsg("成功！！！");
            rv.setData(aa);
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg("失败！！！");
        }

        return rv;
    }


    /**
     * 查余额
     * @param accountVO
     * @return
     */
    @RequestMapping(value = "showBalance",method = {RequestMethod.GET,RequestMethod.POST})
    public @ResponseBody ResultVO showBalance(AccountVO accountVO){
        log.debug("用户请求查询账户");
        ResultVO rv=new ResultVO();
        try {
            Accounts a=new Accounts();
            a.setAccountId(accountVO.getAccountId());
            Accounts aa=accountService.showBalance(a);
            rv.setCode(1);
            rv.setData(aa);
            rv.setMsg("成功！！！");
        }catch (Exception e){
            e.printStackTrace();
            rv.setCode(0);
            rv.setMsg("失败！！！");
        }

        return rv;
    }

}
