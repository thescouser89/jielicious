/*
 * Copyright 2012-2017 Red Hat, Inc.
 *
 * This file is part of Thermostat.
 *
 * Thermostat is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2, or (at your
 * option) any later version.
 *
 * Thermostat is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermostat; see the file COPYING.  If not see
 * <http://www.gnu.org/licenses/>.
 *
 * Linking this code with other modules is making a combined work
 * based on this code.  Thus, the terms and conditions of the GNU
 * General Public License cover the whole combination.
 *
 * As a special exception, the copyright holders of this code give
 * you permission to link this code with independent modules to
 * produce an executable, regardless of the license terms of these
 * independent modules, and to copy and distribute the resulting
 * executable under terms of your choice, provided that you also
 * meet, for each linked independent module, the terms and conditions
 * of the license of that module.  An independent module is a module
 * which is not derived from or based on this code.  If you modify
 * this code, you may extend this exception to your version of the
 * library, but you are not obligated to do so.  If you do not wish
 * to do so, delete this exception statement from your version.
 */

package com.redhat.jielicious.storage.mongo.filters;

import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.conversions.Bson;

import com.mongodb.BasicDBList;

public class MongoRequestFilters {

    public static Bson buildGetFilter(String systemId, String agentId, String jvmId, List<String> tags) {
        List<Bson> filters = new ArrayList<>();

        filters.add(buildIdFilters(systemId, agentId, jvmId));

        filters.add(buildTagsFilter(tags));

        return and(filters);
    }

    public static Bson buildPostFilter(BasicDBList queries, String systemId, String agentId, String jvmId, List<String> tags) {
        List<Bson> filters = new ArrayList<>();

        if (queries != null) {
            filters.add(buildQueriesFilter(queries));
        }

        filters.add(buildIdFilters(systemId, agentId, jvmId));

        filters.add(buildTagsFilter(tags));

        return and(filters);
    }

    private static Bson buildIdFilters(String systemId, String agentId, String jvmId) {
        List<Bson> filters = new ArrayList<>();
        if (systemId != null && !(systemId.equals("all"))) {
            filters.add(eq("systemId", systemId));
        }

        if (agentId != null && !(agentId.equals("all"))) {
            filters.add(eq("agentId", agentId));
        }

        if (jvmId != null && !(jvmId.equals("all"))) {
            filters.add(eq("jvmId", jvmId));
        }
        return and(filters);
    }

    private static Bson buildTagsFilter(List<String> tags) {
        List<Bson> filters = new ArrayList<>();
        filters.add(exists("tags", false));
        if (tags != null && !tags.isEmpty()) {
            for (String tag : tags) {
                filters.add(eq("tags", tag));
            }
        }
        return or(filters);
    }

    private static Bson buildQueriesFilter(BasicDBList queries) {
        List<Bson> filters = new ArrayList<>();
        for (Object q : queries) {
            String filter = q.toString();
            Pattern p = Pattern.compile("(<=|>=|<|>|=)");
            Matcher m = p.matcher(filter);
            if (m.find()) {
                String key = "obj." + filter.substring(0, m.start());
                String comparator = filter.substring(m.start(), m.end());
                String valueInput = filter.substring(m.end());

                Object value;

                try {
                    value = Double.parseDouble(valueInput);
                } catch (Exception e) {
                    if (valueInput.startsWith("\"") && valueInput.endsWith("\"")) {
                        value = valueInput.substring(1, valueInput.length()-1);
                    } else {
                        value = valueInput;
                    }
                }

                switch (comparator) {
                    case "<=":
                        filters.add(lte(key, value));
                        break;
                    case ">=":
                        filters.add(gte(key, value));
                        break;
                    case "=":
                        filters.add(eq(key, value));
                        break;
                    case ">":
                        filters.add(gt(key, value));
                        break;
                    case "<":
                        filters.add(lt(key, value));
                        break;
                }
            }
        }
        return and(filters);
    }

    public static Bson buildDeleteFilter(String systemId, String agentId, String jvmId) {
        return buildIdFilters(systemId, agentId, jvmId);
    }
}
