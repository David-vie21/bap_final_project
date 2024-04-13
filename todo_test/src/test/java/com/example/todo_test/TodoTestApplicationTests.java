package com.example.todo_test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.example.todo_test.Models.Model;
import com.example.todo_test.Servises.ModelService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TodoTestApplicationTests {


    @Autowired
    private ModelService ms;
    Date sd = new GregorianCalendar(2022, 8, 23).getTime();
    Date ed = new GregorianCalendar(2022, 8, 25).getTime();	
    Date cd = new GregorianCalendar(2022, 8, 23).getTime();
    Date lud = new GregorianCalendar(2022, 8, 23).getTime();

   public Model model = new Model("Test_titel", "test_des", false, sd, ed, cd, lud, 1  );
    


    @Test
    public void Test_List()
    {      
        ms.delAll();
        ms.postTODO(model);
        List<Model> models = ms.list();

        assertEquals(1 ,models.size());

        //ms.delAll();
    }

    @Test
    public void Test_GetByID()
    {
        ms.delAll();
        long id = ms.postTODO(model);

        Model model2 = ms.getTODOwithID(id);
        assertEquals(model2.getDesci(), model.getDesci());
        assertEquals(model2.getDone(), model.getDone());
        Date d1 = model2.getCreatedate();
        Date d2 = model.getCreatedate();

        SimpleDateFormat DateFor1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String stringDate1 = DateFor1.format(d1);
        String stringDate2 = DateFor1.format(d2);

        assertEquals(stringDate1, stringDate2);

        ms.delAll();
    }

    @Test
    public void Test_put()
    {    long id = ms.postTODO(model);

        Model m = ms.getTODOwithID(id);

        String desc = "Unit_Test";
        String title = "Unit_Test_Titel";
        Date date = new GregorianCalendar(2000, 1, 1).getTime();
        m.setDesci(desc);
        m.setTitle(title);
        m.setCreatedate(date);

        ms.putTODO(id, m);

        assertEquals( ms.getTODOwithID(id).getDesci(), desc);
        assertEquals( ms.getTODOwithID(id).getTitle(), title);

        SimpleDateFormat DateFor1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String stringDate1 = DateFor1.format(date);
        String stringDate2 = DateFor1.format(ms.getTODOwithID(id).getCreatedate());

        assertEquals(stringDate1, stringDate2);

    }

    @Test
    public void Test_Del()
    {
        ms.postTODO(model);
        ms.postTODO(model);
        ms.postTODO(model);
        ms.postTODO(model);
        ms.delAll();

        assertEquals(0 ,ms.list().size());
    }

}
