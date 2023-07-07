package com.kob.backend.service.impl.user.bot;

import com.kob.backend.mapper.BotMapper;
import com.kob.backend.pojo.Bot;
import com.kob.backend.pojo.User;
import com.kob.backend.service.impl.utils.GetUserUtil;
import com.kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        User user = GetUserUtil.getUserWithToken();

        int bot_id = Integer.parseInt(data.get("bot_id"));

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

        if (title == null || title.length() == 0) {
            map.put("error_message", "Title can not be empty");
            return map;
        }

        if (title.length() > 100) {
            map.put("error_message", "Title length can not be longer than 100");
            return map;
        }

        if (description == null || description.length() == 0) {
            description = "This user is lazy, so has not written a description yet~";
        }

        if (description.length() > 300) {
            map.put("error_message", "Bot description length can not be longer than 300");
            return map;
        }

        if (content == null || content.length() == 0) {
            map.put("error_message", "Bot codes can not be empty");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "Bot codes length can not be longer than 10000");
            return map;
        }

        Bot bot = botMapper.selectById(bot_id);

        if (bot == null) {
            map.put("error_message", "Bot not found or has been deleted");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "You can only edit your own bot");
            return map;
        }

        Bot new_bot = new Bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getCreatetime(),
                new Date()
        );

        botMapper.updateById(new_bot);

        map.put("error_message", "success");

        return map;
    }
}
