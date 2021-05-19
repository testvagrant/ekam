package com.testvagrant.ekam;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.testvagrant.ekam.commons.cache.InjectorsCache;
import com.testvagrant.ekam.commons.cache.InjectorsCacheProvider;
import com.testvagrant.ekam.commons.cache.TestContextCache;
import com.testvagrant.ekam.commons.cache.TestContextCacheProvider;
import com.testvagrant.ekam.commons.exceptions.NoSuchKeyException;
import com.testvagrant.ekam.commons.locale.LocaleFinder;
import com.testvagrant.ekam.commons.modules.LocaleModule;
import com.testvagrant.ekam.commons.testContext.EkamTestContext;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class CacheTest {

    @Test
    public void injectorsCache() {
        Injector injector = Guice.createInjector(new LocaleModule());
        InjectorsCache injectorsCache = new InjectorsCache();
        injectorsCache.put("a", injector);
        Injector injector1 = injectorsCache.get("a");
        Assert.assertNotNull(injector1);
        Assert.assertEquals(injector, injector1);
        LocaleFinder instance = injector1.getInstance(LocaleFinder.class);
        System.out.println(instance);
    }

    @Test
    public void injectorsCacheProviderTest() {
        Injector injector = Guice.createInjector(new LocaleModule());
        InjectorsCache injectorsCache = InjectorsCacheProvider.getInstance();
        injectorsCache.put("a", injector);
        Injector injector1 = injectorsCache.get("a");
        Assert.assertNotNull(injector1);
        Assert.assertEquals(injector, injector1);
        LocaleFinder instance = injector1.getInstance(LocaleFinder.class);
        System.out.println(instance);
    }

    @Test
    public void testContextCacheTest() throws NoSuchKeyException {
        TestContextCache<String, String> instance = TestContextCacheProvider.getInstance();
        String currentTime = LocalDateTime.now().toString();
        System.out.println(currentTime);
        instance.put("buildNo", currentTime);
        Assert.assertEquals(TestContextCacheProvider.getInstance().get("buildNo"), currentTime);
    }

    @Test
    public void testContextCacheTestLockedKeyWithRelease() throws NoSuchKeyException {
        TestContextCache<String, String> instance = TestContextCacheProvider.getInstance();
        String currentTime = LocalDateTime.now().toString();
        System.out.println(currentTime);
        String key = "buildNo";
        instance.put(key, currentTime);
        Assert.assertEquals(TestContextCacheProvider.getInstance().get(key, true), currentTime);
        instance.release(key);
        String buildNo = (String) TestContextCacheProvider.getInstance().get(key);
        System.out.println(buildNo);
    }

    @Test(expectedExceptions = NoSuchKeyException.class, expectedExceptionsMessageRegExp = "buildNo is a shared key and is engaged, please release to access it")
    public void testContextCacheTestLockedKeyWithoutRelease() throws NoSuchKeyException {
        TestContextCache<String, String> instance = TestContextCacheProvider.getInstance();
        String currentTime = LocalDateTime.now().toString();
        System.out.println(currentTime);
        String key = "buildNo";
        instance.put(key, currentTime);
        Assert.assertEquals(TestContextCacheProvider.getInstance().get(key, true), currentTime);
        String buildNo = (String) TestContextCacheProvider.getInstance().get(key);
        System.out.println(buildNo);
    }

    @Test
    public void test() {
        EkamTestContext build = EkamTestContext.builder().featureName("f").testName("t").build();
        EkamTestContext build1 = EkamTestContext.builder().featureName("f").testName("t").build();
        Assert.assertEquals(build.hashCode(), build1.hashCode());
    }
}
