package com.wlwx.dispatch.service.impl;

import com.wlwx.dispatch.entity.dispatch.*;
import com.wlwx.dispatch.mapper.SmsMapper.DispatchMapper;
import com.wlwx.dispatch.mapper.EFDMapper.EFDDispatchMapper;
import com.wlwx.dispatch.service.DispatchService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class DispatchServiceImp implements DispatchService {

    @Resource(name = "efdSqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    private Logger logger = LogManager.getLogger(DispatchServiceImp.class);
    @Resource
    private DispatchMapper dispatchMapper;

    @Resource
    private EFDDispatchMapper efdDispatchMapper;

    @Override
    public List<Smdown> getAllWaitSendSmsTask() throws SQLException {
        return efdDispatchMapper.getAllWaitSendSmsTask();
    }

    @Override
    public void initTaskStatus() throws SQLException {
        efdDispatchMapper.initTaskStatus();
    }

    @Override
    public void updateTaskStatusToEnd(Smdown sm) throws SQLException {
        efdDispatchMapper.updateTaskStatusToEnd(sm);
    }

    @Override
    public List<ProviderMobilePrefix> getProviderMobileList()  throws SQLException {
        return dispatchMapper.getProviderMobileList();
    }

    @Override
    public ProviderMobilePrefix getProviderIdByMobile(String mobile)  throws SQLException {
        return dispatchMapper.getProviderIdByMobile(mobile);
    }


    @Override
    public Customer getCusomerById(int cust_id)  throws SQLException {
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
            if(sqlVo.getType() == 1){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.insert("com.wlwx.dispatch.mapper.EFDMapper.EFDDispatchMapper.insertEfdSmrpt", efdSmrpt);
                    index++;
                    insertNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else if (sqlVo.getType() == 2){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.update("com.wlwx.dispatch.mapper.EFDMapper.EFDDispatchMapper.updateSubmitEfdRpt", efdSmrpt);
                    index++;
                    updateNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else if (sqlVo.getType() == 3){
                for(EfdSmrpt efdSmrpt : sqlVo.getDateList()){
                    sqlSession.update("com.wlwx.dispatch.mapper.EFDMapper.EFDDispatchMapper.updateReportEfdRpt", efdSmrpt);
                    index++;
                    updateNum++;
                    if(index % 500 == 0){
                        sqlSession.commit();
                    }
                }
            }else{
                logger.error("not inster/update Type="+ sqlVo.getType());
            }
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (Exception e){
            logger.error("批量执行异常......",e);
            e.printStackTrace();
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
        long exTime = System.currentTimeMillis() - starTime;
//        if(exTime>1000)
        logger.info("insertNum("+insertNum+"),updateNum("+updateNum+")：执行耗时"+ exTime + "ms..");
    }

    @Override
    public void batchUpdateTaskStatus(List<Smdown> stList) throws SQLException {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try {
            for (Smdown smdown : stList) {
                sqlSession.update("com.wlwx.dispatch.mapper.EFDMapper.EFDDispatchMapper.updateSmdownStatus", smdown);
            }
            sqlSession.commit();
            sqlSession.clearCache();
        } catch (Exception e){
            logger.error("执行异常......",e);
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }


    }
}
