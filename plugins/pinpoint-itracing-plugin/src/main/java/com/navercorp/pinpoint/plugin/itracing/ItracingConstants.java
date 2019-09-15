/*
 * Copyright 2018 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.navercorp.pinpoint.plugin.itracing;

import com.navercorp.pinpoint.common.trace.AnnotationKey;
import com.navercorp.pinpoint.common.trace.AnnotationKeyFactory;
import com.navercorp.pinpoint.common.trace.ServiceType;
import com.navercorp.pinpoint.common.trace.ServiceTypeFactory;

import static com.navercorp.pinpoint.common.trace.AnnotationKeyProperty.VIEW_IN_RECORD_SET;
import static com.navercorp.pinpoint.common.trace.ServiceTypeProperty.RECORD_STATISTICS;

/**
 * @author Jinkai.Ma
 */
public final class ItracingConstants {
    private ItracingConstants() {
    }

    public static final ServiceType ITRACING_COLLECTOR_SERVICE_TYPE = ServiceTypeFactory.of(1123, "ITRACING_COLLECTOR", RECORD_STATISTICS);
    public static final ServiceType ITRACING_WEB_SERVICE_TYPE = ServiceTypeFactory.of(1124, "ITRACING_WEB", RECORD_STATISTICS);
    public static final AnnotationKey ITRACING_LOCAL_ADDRESS_ANNOTATION_KEY = AnnotationKeyFactory.of(170, "itracing.localAddress", VIEW_IN_RECORD_SET);
    public static final AnnotationKey ITRACING_REMOTE_ADDRESS_ANNOTATION_KEY = AnnotationKeyFactory.of(171, "itracing.remoteAddress", VIEW_IN_RECORD_SET);

}
