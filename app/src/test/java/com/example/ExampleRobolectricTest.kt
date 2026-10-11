package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.catalog.PoetsCatalog
import com.example.data.catalog.ResourcesCatalog
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context verifies Sukhan branding`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        val tagline = context.getString(R.string.app_tagline)
        val creator = context.getString(R.string.creator_credit)

        assertEquals("SUKHAN", appName)
        assertEquals("A home for words, poets & untold feelings.", tagline)
        assertEquals("Made by Prince Thakur", creator)
    }

    @Test
    fun `poets catalog contains all mandated poets`() {
        val poets = PoetsCatalog.poets
        assertTrue("Poet catalog should contain at least 12 poets", poets.size >= 12)

        val expectedIds = listOf(
            "ghalib", "jaun_elia", "mir", "faiz",
            "sahir", "faraz", "iqbal", "shakir",
            "firaq", "bulleh_shah", "kabir", "rahat"
        )

        expectedIds.forEach { id ->
            val poet = PoetsCatalog.getPoetById(id)
            assertNotNull("Poet with id $id should be present", poet)
            assertTrue("Poet should have authentic name", poet!!.name.isNotBlank())
            assertTrue("Poet should have original script name", poet.originalName.isNotBlank())
        }
    }

    @Test
    fun `resources catalog search functions correctly`() {
        val meterResults = ResourcesCatalog.searchResources("meter")
        assertTrue("Searching for meter should return results", meterResults.isNotEmpty())

        val radifResults = ResourcesCatalog.searchResources("radif")
        assertTrue("Searching for radif should return results", radifResults.isNotEmpty())
    }
}
