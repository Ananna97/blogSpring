package com.example.blog.config;

import com.example.blog.model.*;
import com.example.blog.repository.RoleRepository;
import com.example.blog.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeederClass implements CommandLineRunner
{
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final CategoryService categoryService;
    private final PostService postService;
    private final RatingService ratingService;
    private final CommentService commentService;

    public SeederClass(PostService postService, RatingService ratingService, CategoryService categoryService, UserService userService, RoleRepository roleRepository, CommentService commentService) {
        this.postService = postService;
        this.ratingService = ratingService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.commentService = commentService;
    }


    @Override
    public  void run (String... args) {
        List<User> users = userService.findAll();
        User u = new User();
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        if (users.isEmpty()) {


            Role user = new Role();
            user.setName("ROLE_USER");
            roleRepository.save(user);

            Role admin = new Role();
            admin.setName("ROLE_ADMIN");
            roleRepository.save(admin);

            u.setFirstName("admin");
            u.setLastName("admin");
            u.setEmail("admin.admin@email.com");
            u.setPassword("admin");
            Set<Role> authoritiesAdmin = new HashSet<>();
            roleRepository.findById("ROLE_ADMIN").ifPresent(authoritiesAdmin::add);
            u.setRoles(authoritiesAdmin);
            userService.save(u);

            u1.setFirstName("Mariano");
            u1.setLastName("Bruni");
            u1.setEmail("mariano.bruni@email.com");
            u1.setPassword("Ciao1");
            Set<Role> rolesUser = new HashSet<>();
            roleRepository.findById("ROLE_USER").ifPresent(rolesUser::add);
            u1.setRoles(rolesUser);
            userService.save(u1);

            u2.setFirstName("Ella");
            u2.setLastName("Fondly");
            u2.setEmail("ella.fondi@email.com");
            u2.setPassword("Hello2");
            u2.setRoles(rolesUser);
            userService.save(u2);

            u3.setFirstName("Dion");
            u3.setLastName("Jolie");
            u3.setEmail("dion.jolie@email.com");
            u3.setPassword("Bonjour3");
            u3.setRoles(rolesUser);
            userService.save(u3);
        }

        List<Category> categories = categoryService.findAll();
        Category c1 = new Category();
        Category c2 = new Category();
        Category c3 = new Category();
        if (categories.isEmpty()) {
            c1.setCategoryName("Christmas");
            categoryService.save(c1);

            c2.setCategoryName("Easter");
            categoryService.save(c2);

            c3.setCategoryName("Common");
            categoryService.save(c3);
        }

        List<Post> posts = postService.findAll();
        Post p1 = new Post();
        Post p2 = new Post();
        Post p3 = new Post();

        if (posts.isEmpty()) {
            p1.setTitle("Christmas traditions");
            p1.setBody("Christmas traditions include a variety of customs, religious practices, rituals, and folklore associated with the celebration of Christmas. Many of these traditions vary by country or region, while others are practiced in a virtually identical manner across the world.");
            p1.setCategory(c1);
            p1.setUser(u1);
            postService.save(p1);

            p2.setTitle("Easter traditions");
            p2.setBody("Easter traditions (also known as Paschal traditions) are customs and practices that are followed in various cultures and communities around the world to celebrate Easter (also known as Paschal or Resurrection Sunday), which is the central feast in Christianity, commemorating the resurrection of Jesus. The Easter season is seen as a time of celebration and feasting, in contrast to the antecedent season of Lent, which is a time of penitence and fasting.");
            p2.setCategory(c2);
            p2.setUser(u2);
            postService.save(p2);

            p3.setTitle("Church attendance");
            p3.setBody("Church attendance: Christmas Day (inclusive of its vigil, Christmas Eve), is a Festival in the Lutheran Church, a Solemnity in the Roman Catholic Church, and a Principal Feast of the Anglican Communion. Other Christian denominations do not rank their feast days but nevertheless place importance on Christmas Eve/Christmas Day, as with other Christian feasts like Easter, Ascension Day, and Pentecost. As such, for Christians, attending a Christmas Eve or Christmas Day church service plays an important part in the recognition of the Christmas season.");
            p3.setCategory(c3);
            p3.setUser(u3);
            postService.save(p3);
        }

        List<Rating> ratings = ratingService.findAll();
        Rating r1 = new Rating();
        Rating r2 = new Rating();
        Rating r3 = new Rating();
        if (ratings.isEmpty()) {
            r1.setValue(10);
            r1.setPost(p2);
            r1.setUser(u1);
            ratingService.save(r1);

            r2.setValue(7);
            r2.setPost(p3);
            r2.setUser(u3);
            ratingService.save(r2);

            r3.setValue(9);
            r3.setPost(p2);
            r3.setUser(u3);
            ratingService.save(r3);
        }

        List<Comment> comments = commentService.findAll();
        Comment co1 = new Comment();
        Comment co2 = new Comment();
        Comment co3 = new Comment();
        if (comments.isEmpty()) {
            co1.setText("I love easter!!!!!");
            co1.setPost(p2);
            co1.setUser(u1);
            commentService.save(co1);

            co2.setText("I really enjoy the Christmas carols!.");
            co2.setPost(p3);
            co2.setUser(u3);
            commentService.save(co2);

            co3.setText("I can not wait for some Panettone and eggs cracking competition.");
            co3.setPost(p2);
            co3.setUser(u3);
            commentService.save(co3);
        }
    }
}