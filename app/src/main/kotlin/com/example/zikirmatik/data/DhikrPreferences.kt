package com.example.zikirmatik.data

import android.content.Context
import android.content.SharedPreferences
import com.example.zikirmatik.model.Dhikr
import com.example.zikirmatik.model.HistoryRecord
import com.example.zikirmatik.model.TasbihTheme
import org.json.JSONArray
import org.json.JSONObject

class DhikrPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("zikirmatik_prefs", Context.MODE_PRIVATE)

    var currentDhikrId: String
        get() = prefs.getString("current_dhikr_id", "subhanallah") ?: "subhanallah"
        set(value) = prefs.edit().putString("current_dhikr_id", value).apply()

    var count: Int
        get() = prefs.getInt("counter_value", 0)
        set(value) = prefs.edit().putInt("counter_value", value).apply()

    var lapCount: Int
        get() = prefs.getInt("lap_count", 0)
        set(value) = prefs.edit().putInt("lap_count", value).apply()

    var totalLifetimeCount: Long
        get() = prefs.getLong("total_lifetime_count", 0L)
        set(value) = prefs.edit().putLong("total_lifetime_count", value).apply()

    var target: Int
        get() = prefs.getInt("target_count", 33)
        set(value) = prefs.edit().putInt("target_count", value).apply()

    var isVibrationEnabled: Boolean
        get() = prefs.getBoolean("vibration_enabled", true)
        set(value) = prefs.edit().putBoolean("vibration_enabled", value).apply()

    var isSoundEnabled: Boolean
        get() = prefs.getBoolean("sound_enabled", true)
        set(value) = prefs.edit().putBoolean("sound_enabled", value).apply()

    var isFullScreenEnabled: Boolean
        get() = prefs.getBoolean("fullscreen_enabled", false)
        set(value) = prefs.edit().putBoolean("fullscreen_enabled", value).apply()

    var selectedTheme: TasbihTheme
        get() {
            val name = prefs.getString("selected_theme", TasbihTheme.EMERALD_GOLD.name)
            return try {
                TasbihTheme.valueOf(name ?: TasbihTheme.EMERALD_GOLD.name)
            } catch (e: Exception) {
                TasbihTheme.EMERALD_GOLD
            }
        }
        set(value) = prefs.edit().putString("selected_theme", value.name).apply()

    fun getCustomDhikrs(): List<Dhikr> {
        val raw = prefs.getString("custom_dhikrs_json", "[]") ?: "[]"
        val list = mutableListOf<Dhikr>()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    Dhikr(
                        id = obj.getString("id"),
                        title = obj.getString("title"),
                        arabic = obj.optString("arabic", ""),
                        meaning = obj.optString("meaning", ""),
                        defaultTarget = obj.optInt("defaultTarget", 33),
                        isCustom = true
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun saveCustomDhikrs(list: List<Dhikr>) {
        val array = JSONArray()
        list.forEach { item ->
            val obj = JSONObject().apply {
                put("id", item.id)
                put("title", item.title)
                put("arabic", item.arabic)
                put("meaning", item.meaning)
                put("defaultTarget", item.defaultTarget)
            }
            array.put(obj)
        }
        prefs.edit().putString("custom_dhikrs_json", array.toString()).apply()
    }

    fun getHistory(): List<HistoryRecord> {
        val raw = prefs.getString("history_records_json", "[]") ?: "[]"
        val list = mutableListOf<HistoryRecord>()
        try {
            val array = JSONArray(raw)
            for (i in 0 until array.length()) {
                val obj = array.getJSONObject(i)
                list.add(
                    HistoryRecord(
                        id = obj.getLong("id"),
                        dhikrTitle = obj.getString("dhikrTitle"),
                        count = obj.getInt("count"),
                        target = obj.getInt("target"),
                        dateText = obj.getString("dateText")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun addHistoryRecord(record: HistoryRecord) {
        val existing = getHistory().toMutableList()
        existing.add(0, record)
        if (existing.size > 100) {
            existing.removeAt(existing.lastIndex)
        }
        val array = JSONArray()
        existing.forEach {
            val obj = JSONObject().apply {
                put("id", it.id)
                put("dhikrTitle", it.dhikrTitle)
                put("count", it.count)
                put("target", it.target)
                put("dateText", it.dateText)
            }
            array.put(obj)
        }
        prefs.edit().putString("history_records_json", array.toString()).apply()
    }

    fun clearHistory() {
        prefs.edit().remove("history_records_json").apply()
    }
}
