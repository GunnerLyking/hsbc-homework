package com.hsbc.incident.dao;

import com.hsbc.incident.entity.Incident;
import com.hsbc.incident.entity.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncidentDaoTest {

    private IncidentDao incidentDao;

    @BeforeEach
    void setUp() {
        // 每次测试前创建一个新的 IncidentDao 实例
        incidentDao = new IncidentDao();
    }

    @Test
    void test1() {
        // 创建事件对象
        Incident incident = new Incident();
        incident.setType("Info");
        incident.setStatus("Pending");
        incident.setMsg("This is a test incident");

        // 添加事件
        incidentDao.add(incident);

        // 检查事件是否添加成功
        assertNotNull(incident.getId(), "事件ID应该不为空");
        assertEquals(1, incident.getId(), "事件ID应该是1");
        assertEquals(1, incidentDao.query(1, 5).getTotal(), "应该有一个事件在列表中");
    }

    @Test
    void test2() {
        // 创建并添加事件
        Incident incident = new Incident();
        incident.setType("Warning");
        incident.setStatus("In Progress");
        incident.setMsg("Test incident for deletion");
        incidentDao.add(incident);

        // 删除事件
        incidentDao.delete(incident.getId());

        // 确认删除
        assertNull(incidentDao.getById(incident.getId()), "事件应该已被删除");
        assertEquals(0, incidentDao.query(1, 5).getTotal(), "删除后应该没有事件");
    }

    @Test
    void test3() {
        // 创建并添加事件
        Incident incident = new Incident();
        incident.setType("Error");
        incident.setStatus("Pending");
        incident.setMsg("Initial message");
        incidentDao.add(incident);

        // 修改事件
        incident.setMsg("Updated message");
        incidentDao.modify(incident);

        // 确认修改
        Incident modifiedIncident = incidentDao.getById(incident.getId());
        assertNotNull(modifiedIncident, "事件应该存在");
        assertEquals("Updated message", modifiedIncident.getMsg(), "事件消息应该已被更新");
    }

    @Test
    void test4() {
        // 创建并添加事件
        Incident incident = new Incident();
        incident.setType("Error");
        incident.setStatus("Resolved");
        incident.setMsg("Test incident for getById");
        incidentDao.add(incident);

        // 获取事件
        Incident fetchedIncident = incidentDao.getById(incident.getId());

        // 确认获取到的事件信息正确
        assertNotNull(fetchedIncident, "事件应该存在");
        assertEquals(incident.getId(), fetchedIncident.getId(), "事件ID应该匹配");
        assertEquals("Test incident for getById", fetchedIncident.getMsg(), "事件消息应该匹配");
    }

    @Test
    void test5() {
        // 添加一些事件
        Incident incident1 = new Incident();
        incident1.setType("Info");
        incident1.setStatus("Pending");
        incident1.setMsg("First test incident");
        incidentDao.add(incident1);

        Incident incident2 = new Incident();
        incident2.setType("Warning");
        incident2.setStatus("In Progress");
        incident2.setMsg("Second test incident");
        incidentDao.add(incident2);

        // 查询事件
        Page<Incident> page = incidentDao.query(1, 2);

        // 确认查询结果
        assertNotNull(page, "查询结果不应该为空");
        assertEquals(2, page.getTotal(), "总事件数量应该为2");
        assertEquals(2, page.getContent().size(), "每页应该显示2条事件");
    }

    @Test
    void test6() {
        // 查询没有添加任何事件时
        Page<Incident> page = incidentDao.query(1, 5);

        // 确认查询结果为空
        assertNotNull(page, "查询结果不应该为空");
        assertEquals(0, page.getTotal(), "没有事件时总数量应该为0");
        assertEquals(0, page.getContent().size(), "没有事件时内容应该为空");
    }
}
