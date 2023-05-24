package io.vincent.learning.stack.jvm.clazzformat;

/**
 * TestPublicFinal.
 *
 * @author Vincent.Lu.
 * @since 2023/4/27
 */
public final class TestPublicFinal {

    public static final String individual = "individual.lu";

    private Long id = 4526L;
    public String name = "Vincent";
    Integer age = 18;

    public void rename(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestPublicFinal{" +
                "individual=" + individual +
                ", id=" + id +
                ", name=" + name +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) {
        TestPublicFinal obj = new TestPublicFinal();
        obj.rename("Vincent.Lu");
        System.out.println(obj);
    }
}
