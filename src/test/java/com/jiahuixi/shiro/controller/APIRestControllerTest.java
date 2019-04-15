package com.jiahuixi.shiro.controller;

import com.jiahuixi.shiro.JiahuixiShiroApplication;
import com.jiahuixi.shiro.model.JsonResult;
import com.jiahuixi.shiro.service.SystemRoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author jiahuixi
 * @Description:
 * @date 2018/11/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JiahuixiShiroApplication.class)
public class APIRestControllerTest extends ApplicationTest {
    private final Logger LOGGER = LoggerFactory.getLogger(APIRestControllerTest.class);
  /**
   * 模拟mvc测试对象
   */
  private MockMvc mockMvc;

  /**
   * web项目上下文
   */
  @Autowired
  private WebApplicationContext webApplicationContext;

  /**
   * 业务数据接口
   */
  @Autowired
  SystemRoleService systemRoleService;

    public static void main(String[] args) {
        System.out.println(JsonResult.success("hahahaha"));
        System.out.println(JsonResult.failed("filde"));


    }
  /**
   * 所有测试方法执行之前执行该方法
   */
  @Before
  public void before() {
    //获取mockmvc对象实例
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }
  @Test
  public void getApiList() throws Exception {
    MvcResult mvcResult = mockMvc
            .perform(// 1
                    MockMvcRequestBuilders.post("/api/list") // 2
                            .param("keyword","key")
                            .param("itemPerPage","5")
                            .param("userby","5")
                            .param("filterBy","5")
                            .param("pageNum","5")// 3
            )
            .andReturn();// 4
    int status = mvcResult.getResponse().getStatus(); // 5
    String responseString = mvcResult.getResponse().getContentAsString(); // 6
      System.out.println(responseString);
    //Assert.assertEquals("请求错误", 200, status); // 7
    //Assert.assertEquals("返回结果不一致", "/demoPage/listPage", responseString); // 8
  }
    @Test
    public void getApiVList() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.post("/api/list") // 2
                                .param("keyword","t")
                                .param("pageSize","10")
                                .param("userby","5")
                                .param("filterBy","5")
                                .param("pageNum","1")// 3
                )
                .andReturn();// 4
        int status = mvcResult.getResponse().getStatus(); // 5
        String responseString = mvcResult.getResponse().getContentAsString(); // 6
        System.out.println(responseString);
    }
    @Test
    public void getAPIDetail() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(// 1
                        MockMvcRequestBuilders.post("/api/detail/test") // 2
                               // .param("apiId","test")
                )
                .andReturn();// 4
        int status = mvcResult.getResponse().getStatus(); // 5
        String responseString = mvcResult.getResponse().getContentAsString(); // 6
        System.out.println(responseString);
    }
}