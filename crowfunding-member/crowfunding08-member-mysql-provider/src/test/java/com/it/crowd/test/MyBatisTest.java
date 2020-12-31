package com.it.crowd.test;

import com.it.crowd.mapper.MemberPOMapper;
import com.it.crowd.mapper.ProjectPOMapper;
import com.it.crowd.po.MemberPO;
import com.it.crowd.vo.DetailProjectVO;
import com.it.crowd.vo.DetailReturnVO;
import com.it.crowd.vo.PortalProjectVO;
import com.it.crowd.vo.PortalTypeVO;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wyj
 * @description
 * @create 2020-12-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest
{

    @Autowired
    private DataSource dataSource;

    @Autowired
    private MemberPOMapper memberPOMapper;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    private Logger logger = LoggerFactory.getLogger(MyBatisTest.class);

    @Test
    public void testLoadDetailProjectVO()
    {
        DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(3);
        if (detailProjectVO == null)
            return;
        logger.info(detailProjectVO.getProjectId() + " ");
        logger.info(detailProjectVO.getProjectName() + " ");
        logger.info(detailProjectVO.getProjectDesc() + " ");
        logger.info(detailProjectVO.getFollowerCount() + " ");
        logger.info(detailProjectVO.getMoney() + " ");
        logger.info(detailProjectVO.getSupportMoney() + " ");
        logger.info(detailProjectVO.getPercentage() + " ");
        logger.info(detailProjectVO.getDeployDate() + " ");
        logger.info(detailProjectVO.getLastDay() + " ");
        logger.info(detailProjectVO.getSupporterCount() + " ");
        logger.info(detailProjectVO.getHeaderPicturePath() + " ");

        List<String> detailPicturePathList = detailProjectVO.getDetailPicturePathList();
        for (String s : detailPicturePathList)
        {
            logger.info("detail path" + s);
        }
        List<DetailReturnVO> detailReturnVOList = detailProjectVO.getDetailReturnVOList();
        for (DetailReturnVO detailReturnVO : detailReturnVOList)
        {
            logger.info(detailReturnVO.toString());
        }
    }

    @Test
    public void testLoadTypeData()
    {
        List<PortalTypeVO> typeVOList = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : typeVOList)
        {
            String name = portalTypeVO.getName();
            String remark = portalTypeVO.getRemark();
            logger.info("name = " + name + ",remark = " + remark);
            List<PortalProjectVO> portalProjectVOList = portalTypeVO.getPortalProjectVOList();
            for (PortalProjectVO portalProjectVO : portalProjectVOList)
            {
                if (portalProjectVO == null)
                    continue;
                logger.info(portalProjectVO.toString());
            }
        }
    }

    @Test
    public void testMapper(){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String source = "123123";
        String encode = passwordEncoder.encode(source);
        memberPOMapper.insert(new MemberPO(null,"jack",encode,"杰克","jack@qq.com",1,1,"杰克","123123",2));
    }
    @Test
    public void testConnection() throws SQLException
    {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }
}
