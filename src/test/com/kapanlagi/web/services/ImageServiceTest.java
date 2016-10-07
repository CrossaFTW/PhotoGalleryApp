package com.kapanlagi.web.services;

import static org.junit.Assert.*;

/**
 * Created by Crossa on 10/4/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/application-test.xml"})
public class ImageServiceTest {
    @Autowired
    ImageService imageService;

    @Test
    public void resizeImage() throws Exception {
        imageService.resizeImage( /* implementation here */ );
    }

}