/*
 * Copyright 2015 The SageTV Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sage.epg.sd.json.programs;

import sage.epg.sd.gson.JsonDeserializationContext;
import sage.epg.sd.gson.JsonDeserializer;
import sage.epg.sd.gson.JsonElement;
import sage.epg.sd.gson.JsonObject;
import sage.epg.sd.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Map;

public class SDMetadataDeserializer implements JsonDeserializer<SDProgramMetadata>
{
  @Override
  public SDProgramMetadata deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
  {
    if (!json.isJsonObject())
    {
      throw new JsonParseException("Metadata source must be an object. " + json.toString());
    }

    String provider = null;
    int season = 0;
    int episode = 0;
    int totalSeasons = 0;
    int totalEpisodes = 0;

    for (Map.Entry<String, JsonElement> source : ((JsonObject)json).entrySet())
    {
      JsonElement element = source.getValue();
      if (!element.isJsonObject())
      {
        throw new JsonParseException("Metadata source data must be an object. " + json.toString());
      }

      provider = source.getKey();

      for (Map.Entry<String, JsonElement> data : ((JsonObject)element).entrySet())
      {
        switch (data.getKey())
        {
          case "season":
            season = data.getValue().getAsInt();
            break;
          case "episode":
            episode = data.getValue().getAsInt();
            break;
          case "totalSeasons":
            totalSeasons = data.getValue().getAsInt();
            break;
          case "totalEpisodes":
            totalEpisodes = data.getValue().getAsInt();
            break;
        }
      }
    }

    return new SDProgramMetadata(provider, season, episode, totalSeasons, totalEpisodes);
  }
}
