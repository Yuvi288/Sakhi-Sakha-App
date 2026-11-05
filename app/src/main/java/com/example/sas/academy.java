package com.example.sas;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sas.adapters.CourseAdapter;
import com.example.sas.models.Course;
import java.util.ArrayList;
import java.util.List;

public class academy extends AppCompatActivity {

    private RecyclerView upcomingCoursesRecyclerView, myCoursesRecyclerView, instructorsRecyclerView, resourcesRecyclerView;
    private CourseAdapter courseAdapter;
    private List<Course> upcomingCoursesList, myCoursesList, instructorsList, resourcesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy);

        // Initialize RecyclerViews
        upcomingCoursesRecyclerView = findViewById(R.id.upcomingCoursesRecyclerView);
        myCoursesRecyclerView = findViewById(R.id.myCoursesRecyclerView);
        instructorsRecyclerView = findViewById(R.id.instructorsRecyclerView);
        resourcesRecyclerView = findViewById(R.id.resourcesRecyclerView);

        // Set Layout Managers
        upcomingCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        myCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        instructorsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        resourcesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Load Data
        loadUpcomingCourses();
        loadMyCourses();
        loadInstructors();
        loadResources();

        // Set Adapters
        courseAdapter = new CourseAdapter(upcomingCoursesList);
        upcomingCoursesRecyclerView.setAdapter(courseAdapter);

        courseAdapter = new CourseAdapter(myCoursesList);
        myCoursesRecyclerView.setAdapter(courseAdapter);

        courseAdapter = new CourseAdapter(instructorsList);
        instructorsRecyclerView.setAdapter(courseAdapter);

        courseAdapter = new CourseAdapter(resourcesList);
        resourcesRecyclerView.setAdapter(courseAdapter);
    }

    private void loadUpcomingCourses() {
        upcomingCoursesList = new ArrayList<>();
        upcomingCoursesList.add(new Course("Bridal Makeup", "Learn to create stunning bridal looks."));
        upcomingCoursesList.add(new Course("Advanced Hair Styling", "Master professional hair styling techniques."));
    }

    private void loadMyCourses() {
        myCoursesList = new ArrayList<>();
        myCoursesList.add(new Course("Basic Skincare", "Learn the fundamentals of skincare and facial treatments."));
    }

    private void loadInstructors() {
        instructorsList = new ArrayList<>();
        instructorsList.add(new Course("Instructor: Sarah Johnson", "Expert in Makeup and Beauty."));
        instructorsList.add(new Course("Instructor: Mike Brown", "Professional Hair Stylist."));
    }

    private void loadResources() {
        resourcesList = new ArrayList<>();
        resourcesList.add(new Course("PDF: Skin Care Tips", "Download a guide on skincare essentials."));
        resourcesList.add(new Course("Video: Nail Art Designs", "Learn beautiful nail art techniques."));
    }
}
