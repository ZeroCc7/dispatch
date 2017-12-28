package com.wlwx.dispatch.service.impl;

import com.wlwx.dispatch.entity.dispatch.*;
import com.wlwx.dispatch.mapper.DispatchMapper;
import com.wlwx.dispatch.service.DispatchService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class DispatchServiceImp implements DispatchService {

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    DispatchMapper dispatchMapper;

    @Override
    public List<Smdown> getAllWaitSendSmsTask() throws SQLException {
        return dispatchMapper.getAllWaitSendSmsTask();
    }

    @Override
    public void initTaskStatus() throws SQLException {
        dispatchMapper.initTaskStatus();
    }

    @Override
    public void updateTaskStatusToEnd(Smdown sm) throws SQLException {
        dispatchMapper.updateTaskStatusToEnd(sm);
    }

    @Override
    public List<ProviderMobilePrefix> getProviderMobileList() {
        return dispatchMapper.getProviderMobileList();
    }

    @Override
    public ProviderMobilePrefix getProviderIdByMobile(String mobile) {
        return dispatchMapper.getProviderIdByMobile(mobile);
    }


    @Override
    public Customer getCusomerById(int cust_id) {
        return dispatchMapper.getCusomerById(cust_id);
    }

    @Override
    public void BatchExcuteSql(SqlVo sqlVo) {
        long starTime = System.currentTimeMillis();

        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        int index = 0;
        int insertNum = 0;
        int updateNum = 0;
        try {
//			for (SqlVo sqlVo : sqlVoList) {
            if(sqlVo.getType() == 1){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.insert("com.wlwx.dispatch.mapper.DispatchMapper.insertEfdSmrpt", efdSmrpt);
                    index++;
                    insertNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else if (sqlVo.getType() == 2){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.update("com.wlwx.dispatch.mapper.DispatchMapper.updateSubmitEfdRpt", efdSmrpt);
                    index++;
                    updateNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else if (sqlVo.getType() == 3){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.update("com.wlwx.dispatch.mapper.DispatchMapper.updateReportEfdRpt", efdSmrpt);
                    index++;
                    updateNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else{
//                PublicConstants.tasklog.info("not inster/update Type="+ sqlVo.getType());
            }
//			}
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (Exception e){
//            PublicConstantsonstants.tasklog.info("批量执行异常......",e);
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
        long exTime = System.currentTimeMillis() - starTime;
//        if(exTime>1000)
//            PublicConstants.tasklog.info("insertNum("+insertNum+"),updateNum("+updateNum+")：执行耗时"+ exTime + "ms..");
    }

    @Override
    public void batchUpdateTaskStatus(List<Smdown> stList) throws SQLException {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try {
            for (Smdown smdown : stList) {
                sqlSession.update("com.wlwx.dispatch.mapper.DispatchMapper.updateSmdownStatus", smdown);
            }
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (Exception e){
//            PublicConstants.tasklog.info("执行异常......",e);
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }


    }
}
