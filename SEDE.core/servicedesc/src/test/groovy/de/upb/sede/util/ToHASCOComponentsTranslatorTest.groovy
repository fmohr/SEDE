package de.upb.sede.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import de.upb.sede.ISDLLookupService
import de.upb.sede.SDLBase
import de.upb.sede.SDLBaseLookupService
import de.upb.sede.SDLReader
import de.upb.sede.ServiceCollectionDesc
import spock.lang.Specification

import java.util.function.Predicate

import static de.upb.sede.util.ToHASCOComponentsTranslator.*


class ToHASCOComponentsTranslatorTest extends Specification {


    private  static ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    def "test basics translation"() {
        when:
        def sdlBase = SDLGUtil.read {
            collection 'collection.1', {
                service 'service.0', {
                    // no params
                }
                service 'service.1', {
                    implOf 'interface1', 'interface2', 'interface3'
                    params {

                        requiredInterfaces 'interface4', 'interface5', 'interface6'

                        bool 'bParam1', true, false
                        bool 'bParam2', false, false
                        bool 'bParam3', false, true

                        numeric name: 'nParam1',
                            default: 10.4,
                            min: 0.4,
                            max: 20.4,
                            isInteger: false,
                            splitsRefined: 4,
                            minInterval: 10,
                            optional: false

                        numeric name: 'nParam2',
                            default: 10.4,
                            min: 0.4,
                            max: 20.4,
                            isInteger: true,
                            splitsRefined: 4,
                            minInterval: 10,
                            optional: false

                        category name: 'cParam1',
                            default: 'A',
                            categories: ['A', 'B', 'C'],
                            optional: false


                        category name: 'cParam2',
                            default: 'A',
                            categories: [],
                            optional: false


                        dependency "bParam1 in {true}", "nParam2 in [0.0,1.0]"

                        dependency "bParam1 in {false}", "nParam2 in [10.0,20.0]"

                    }
                }
            }
        }

        def lookupService = new SDLBaseLookupService(sdlBase)
        def componentRepos = componentsOfServiceQualifiers(lookupService,
            ['service.0', 'service.1'])
        println(MAPPER.writeValueAsString(sdlBase))
        println(MAPPER.writeValueAsString(componentRepos))
        then:
        componentRepos.size() == 1
        componentRepos[0][KEY_REPO] == 'collection.1'

        when:
        List components = componentRepos[0][KEY_COMPONENTS] as List

        then:
        components.size() == 1
        components[0][KEY_NAME] == 'service.1'

        when:
        def service1Component = components[0]
        then:
        service1Component[KEY_NAME] == 'service.1'
        service1Component[KEY_REQ_INTFACE] == ['interface4', 'interface5', 'interface6']
        service1Component[KEY_PVD_INTFACE] == ['interface1', 'interface2', 'interface3']

        when:
        def params = service1Component[KEY_PARAMETER] as List<Map>

        then:
        params[0][KEY_NAME] == 'bParam1'
        params[0][KEY_DEFAULT] == true
        params[0][KEY_TYPE] == PARAM_TYPE_BOOLEAN
        params[1][KEY_NAME] == 'bParam2'
        params[1][KEY_DEFAULT] == false
        params[1][KEY_TYPE] == PARAM_TYPE_BOOLEAN

        params[2] == [
            (KEY_NAME): 'nParam1',
            (KEY_DEFAULT): 10.4,
            (KEY_MIN): 0.4,
            (KEY_MAX): 20.4,
            (KEY_REFINE_SPLITS): 4,
            (KEY_MIN_INTERVAL): 10,
            (KEY_TYPE): PARAM_TYPE_DOUBLE
        ]
        params[3] == [
            (KEY_NAME): 'nParam2',
            (KEY_DEFAULT): 10.0,
            (KEY_MIN): 0.0,
            (KEY_MAX): 20.0,
            (KEY_REFINE_SPLITS): 4,
            (KEY_MIN_INTERVAL): 10,
            (KEY_TYPE): PARAM_TYPE_INT
        ]
        params[4] == [
            (KEY_NAME): 'cParam1',
            (KEY_DEFAULT): 'A',
            (KEY_VALUES): ['A', 'B', 'C'],
            (KEY_TYPE): PARAM_TYPE_CATEGORY
        ]
        params[5] == [
            (KEY_NAME): 'cParam2',
            (KEY_DEFAULT): 'A',
            (KEY_VALUES): [],
            (KEY_TYPE): PARAM_TYPE_CATEGORY
        ]

        when:
        def deps = service1Component[KEY_DEPENDENCIES] as List
        then:
        deps.size() == 2
        deps[0][KEY_DEPENDENCY_PRE] == "bParam1 in {true}"
        deps[0][KEY_DEPENDENCY_POST] == "nParam2 in [0.0,1.0]"
        deps[1][KEY_DEPENDENCY_PRE] == "bParam1 in {false}"
        deps[1][KEY_DEPENDENCY_POST] == "nParam2 in [10.0,20.0]"
    }

}
